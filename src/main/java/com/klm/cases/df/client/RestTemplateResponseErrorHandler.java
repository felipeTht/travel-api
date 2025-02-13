package com.klm.cases.df.client;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RestTemplateResponseErrorHandler 
  implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) 
      throws IOException {

        return (
          httpResponse.getStatusCode().series() == Series.CLIENT_ERROR 
          || httpResponse.getStatusCode().series() == Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) 
      throws IOException {

        if (httpResponse.getStatusCode()
          .series() == HttpStatus.Series.SERVER_ERROR) {
        	log.error("Mock Api returned a Server Error");
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Error");
        } else if (httpResponse.getStatusCode()
          .series() == HttpStatus.Series.CLIENT_ERROR) {
        	if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
        		log.error("Mock Api returned a 404");
        	}
        	throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not Found");
        }
    }
}