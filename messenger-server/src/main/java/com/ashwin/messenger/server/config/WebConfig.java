package com.ashwin.messenger.server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableWebMvc
@Import(MessengerConfig.class)
public class WebConfig extends WebMvcConfigurationSupport {}