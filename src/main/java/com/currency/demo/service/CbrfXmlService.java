package com.currency.demo.service;

import com.currency.demo.xml.ValCurs;
import org.springframework.stereotype.Service;

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
import java.util.List;

@Service
public class CbrfXmlService implements CbrfService {

    private HttpClient httpClient;

    public CbrfXmlService(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public List<ValCurs.Valute> getCurrencies() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://www.cbr.ru/scripts/XML_daily.asp"))
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
    public List<ValCurs.Valute> getOneCurrency(LocalDate date, String valuteId) {
        return null;
    }

}
