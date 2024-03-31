package com.example.demo.exception.ticket;

public class TicketNotOwnedException extends RuntimeException {
    public TicketNotOwnedException() {

    }
    public TicketNotOwnedException(String message) {
        super(message);
    }
}
