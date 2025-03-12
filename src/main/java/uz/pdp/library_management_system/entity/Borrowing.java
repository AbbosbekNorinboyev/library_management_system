package uz.pdp.library_management_system.entity;

import java.time.LocalDate;

public class Borrowing {
    private Long id;
    private Book book;
    private LocalDate borrowDate; // kitobni ijaraga olingan sanasi
    private LocalDate dueDate; // kitobni qaytarish sanasi
    private LocalDate returnDate; // kitobni qaytarilgan sanasi

    private String status;
}
