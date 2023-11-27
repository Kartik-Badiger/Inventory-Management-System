package com.example.miniproject.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.web.bind.annotation.*;
public abstract class BaseLoggerController {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    // common logging methods and logic can go here...

}
