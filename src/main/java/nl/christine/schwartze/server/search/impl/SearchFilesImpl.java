/*
 * Copyright (c) 2020, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.search.impl;

import nl.christine.schwartze.server.dao.LetterDao;
import nl.christine.schwartze.server.model.Letter;
import nl.christine.schwartze.server.properties.SchwartzeProperties;
import nl.christine.schwartze.server.search.SearchFiles;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component("searchFiles")
public class SearchFilesImpl implements SearchFiles {

    Logger logger = Logger.getLogger(SearchFilesImpl.class);

    @Autowired
    private SchwartzeProperties properties;

    @Autowired
    private LetterDao letterDao;

    private String lettersDirectory;

    String indexPath;
    private String textDocumentName;
    Path docDir;

    @PostConstruct
    public void init() {

        lettersDirectory = properties.getProperty("letters_directory");
        docDir = Paths.get(lettersDirectory);
        indexPath = lettersDirectory + "/indexedFiles";
        textDocumentName = properties.getProperty("text_document_name");
    }

    @Override
    public List<Letter> search(String searchTerm) throws Exception {

        List<Letter> letters = new ArrayList<>();
        IndexSearcher searcher = createSearcher();

        TopDocs foundDocs = searchInContent(searchTerm, searcher);
        List<Document> documents = new ArrayList<>();
        for (ScoreDoc scoreDoc : foundDocs.scoreDocs) {
            documents.add(searcher.doc(scoreDoc.doc));
        }
       return documents.stream().map(doc -> getLetter(doc)).collect(Collectors.toList());
    }

    private Letter getLetter(Document doc){

        String documentNumber = doc.getField("path").stringValue().replace(lettersDirectory, "").replace(textDocumentName, "").replace("/","");

        if(NumberUtils.isCreatable(documentNumber)){
            return letterDao.getLetterForNumber(Integer.parseInt(documentNumber));
        }
        return new Letter();
    }

    private TopDocs searchInContent(String textToFind, IndexSearcher searcher) throws Exception {
        QueryParser qp = new QueryParser("contents", new StandardAnalyzer());
        Query query = qp.parse(textToFind);
        TopDocs hits = searcher.search(query, 10);
        return hits;
    }

    private IndexSearcher createSearcher() throws IOException {
        Directory dir = FSDirectory.open(Paths.get(indexPath));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        return searcher;
    }
}
