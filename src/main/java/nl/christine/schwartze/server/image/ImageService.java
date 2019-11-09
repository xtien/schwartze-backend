/*
 * Copyright (c) 2019, Zaphod Consulting BV, Christine Karman
 * This project is free software: you can redistribute it and/or modify it under the terms of
 * the Apache License, Version 2.0. You can find a copy of the license at
 * http://www. apache.org/licenses/LICENSE-2.0.
 */

package nl.christine.schwartze.server.image;

import java.util.List;

/**
 * User: christine
 * Date: 12/28/18 6:18 PM
 */
public interface ImageService {

    List<String> getImages(int letterNumber);
}
