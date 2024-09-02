package com.thesis.pfm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dataPresaInCarico;
    private LocalDate dataEsecuzione;
    private String numeroOrdine;
    private String ordinante;
    private String filiale;
    private String beneficiario;
    private String indirizzo;
    private String localita;
    private String paese;
    private String iban;
    private String bic;
    private String debitoreEffettivo;
    private String creditoreEffettivo;
    private String identificativoBonifico;
    private String tipologia;
    private String bancaBeneficiario;
    private String descrizione;

    @Column(precision = 19, scale = 4)
    private BigDecimal importo;

    private String TRN;

    @Column(precision = 19, scale = 4)
    private BigDecimal commissioni;

    @Column(precision = 19, scale = 4)
    private BigDecimal totaleOperazione;

    @ManyToOne
    @JoinColumn(name = "numero_conto_corrente", nullable = false)
    @JsonBackReference
    private Account account;

    // Relazione Many-to-One con TransactionCategory
    @ManyToOne
    @JoinColumn(name = "transaction_category_id")
    private TransactionCategory transactionCategory;
}
