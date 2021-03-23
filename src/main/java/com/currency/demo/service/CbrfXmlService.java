package com.currency.demo.service;

import com.currency.demo.xml.ValCurs;
import com.currency.demo.xml.ValCursRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CbrfXmlService implements CbrfService {

    private final HttpClient httpClient;

    public CbrfXmlService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public List<ValCurs.Valute> getCurrencies(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");

        var uri = UriComponentsBuilder.fromUriString("http://www.cbr.ru/scripts/XML_daily.asp")
                .queryParam("date_req", date.format(formatter))
                .build().toUri();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(uri)
                .build();
        String xml = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();
        try {
            var inputStreamReader = new InputStreamReader(
                    new ByteArrayInputStream(xml.getBytes()),
                    StandardCharsets.UTF_8
            );
            JAXBContext jaxbContext = JAXBContext.newInstance(ValCurs.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            ValCurs valute = (ValCurs) jaxbUnmarshaller.unmarshal(inputStreamReader);
            return valute.getValute();
        } catch (Exception ex) {
            throw new RuntimeException("Ошибка при парсинге xml валют");
        }
    }

    @Override
    public ValCursRecord.Record getOneCurrency(LocalDate date, String valuteId) {
        DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        String dateStr = date.format(formatters);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI
                        .create(
                                "http://www.cbr.ru/scripts/XML_dynamic.asp?"
                                        + "date_req1=" + dateStr + "&date_req2=" + dateStr + "&VAL_NM_RQ="
                                        + valuteId
                        )
                )
                .build();

        String xml = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();

        try {
            var inputStreamReader = new InputStreamReader(
                    new ByteArrayInputStream(xml.getBytes()),
                    StandardCharsets.UTF_8
            );
            JAXBContext jaxbContext = JAXBContext.newInstance(ValCursRecord.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            ValCursRecord valute = (ValCursRecord) jaxbUnmarshaller.unmarshal(inputStreamReader);
            List<ValCursRecord.Record> valuteRecords = valute.getRecord();

            if (valuteRecords.size() != 1) {
                throw new RuntimeException("Ожидалась одна валюта");
            }

            return valuteRecords.get(0);
        } catch (Exception ex) {
            throw new RuntimeException("Ошибка при парсинге xml валют", ex);
        }
    }

}
