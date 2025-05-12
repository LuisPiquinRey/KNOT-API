package com.luispiquinrey.KnotCommerce.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

public class ServletKnot extends HttpServlet {

    @Override
    public void init() throws ServletException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy HH:mm:ss");
        String formattedDate = LocalDateTime.now().format(formatter);

        final String GREEN = "\u001B[32m";
        final String BLUE = "\u001B[34m";
        final String YELLOW = "\u001B[33m";
        final String RESET = "\u001B[0m";

        System.out.println(GREEN + "✅ ServletKnot inicializado correctamente!" + RESET);
        System.out.println(BLUE + "📅 Fecha y hora: " + YELLOW + formattedDate + RESET);
        System.out.println(GREEN + "🚀 Listo para recibir peticiones!" + RESET);

        super.init();
    }
}

