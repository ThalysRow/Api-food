package com.api_food.Algaworks_Food.domain.service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.Map;
import java.util.Set;

public interface EmailSendService {

    void send(EmailMessage message);

    @Getter
    @Builder
    class EmailMessage {

        @Singular
        private Set<String> recipients;
        @NonNull
        private String subject;
        @NonNull
        private String body;

        @Singular
        private Map<String, Object> models;

    }

}
