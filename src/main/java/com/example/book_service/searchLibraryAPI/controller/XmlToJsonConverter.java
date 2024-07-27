package com.example.book_service.searchLibraryAPI.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;

public class XmlToJsonConverter {
    private static final XmlMapper xmlMapper = new XmlMapper();
    private static final ObjectMapper jsonMapper = new ObjectMapper();

    public static String convertXmlToJson(String xml) throws IOException {
        // XML을 JsonNode로 변환
        JsonNode xmlNode = xmlMapper.readTree(xml.getBytes());
        // JsonNode를 JSON 문자열로 변환
        return jsonMapper.writeValueAsString(xmlNode);
    }
}
