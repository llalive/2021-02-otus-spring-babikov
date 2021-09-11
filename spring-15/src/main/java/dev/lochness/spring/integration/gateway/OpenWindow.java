package dev.lochness.spring.integration.gateway;

import dev.lochness.spring.integration.domain.Creature;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface OpenWindow {

    @Gateway(requestChannel = "room", replyChannel = "street")
    Creature enter(Creature creature);
}
