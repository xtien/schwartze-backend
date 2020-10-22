/*
 * Copyright (c) 2020, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www.apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.search.impl;

import nl.christine.schwartze.server.properties.SchwartzeProperties;
import nl.christine.schwartze.server.search.IndexFiles;
import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * https://howtodoinjava.com/lucene/lucene-index-and-search-text-files/
 */
@Component("indexFiles")
public class IndexFilesImpl implements IndexFiles {

    Logger logger = Logger.getLogger(IndexFilesImpl.class);

    @Autowired
    private SchwartzeProperties properties;

    private String lettersDirectory;
    private String textDocumentName;

    String indexPath;
    Path docDir;

    @PostConstruct
    public void init() {
        lettersDirectory = properties.getProperty("letters_directory");
        textDocumentName = properties.getProperty("text_document_name");
        docDir = Paths.get(lettersDirectory);
        indexPath = properties.getProperty("index_directory");

        File indexDir = new File(indexPath);
        if(!indexDir.exists()){
            indexDir.mkdir();
        }
    }

    @Override
    public int indexFiles() {

        int count = 0;

        try {
            Directory dir = FSDirectory.open(Paths.get(indexPath));

            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
            iwc.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            IndexWriter writer = new IndexWriter(dir, iwc);
            count = indexDocs(writer, docDir);
            writer.close();

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return count;
    }

    private int indexDocs(final IndexWriter writer, Path path) throws IOException {

        int count = 0;
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(path, filter())) {

            for (Path p : ds) {

                if(p.endsWith("701")){
                    logger.info("ok");
                }

                Path textFile = Paths.get(p.toString() + "/" + textDocumentName);

                if (textFile != null && Files.exists(textFile)) {
                    BasicFileAttributes attrs = Files.readAttributes(p, BasicFileAttributes.class);
                    indexDoc(writer, textFile, attrs.lastModifiedTime().toMillis());
                    System.out.println(p.getFileName());
                    count++;
                }
            }
            logger.info(count + " entries read");
        } catch (IOException ex) {
            logger.error(ex.getMessage(), ex);
        }

        return count;
    }

    private void indexDoc(IndexWriter writer, Path file, long lastModified) throws IOException {

        Document doc = new Document();
        doc.add(new StringField("path", file.toString(), Field.Store.YES));
        doc.add(new LongPoint("modified", lastModified));
        doc.add(new TextField("contents", new String(Files.readAllBytes(file)), Field.Store.YES));

        writer.updateDocument(new Term("path", file.toString()), doc);
    }

    private DirectoryStream.Filter<Path> filter() {
        return entry -> Files.isDirectory(entry);
    }
}
