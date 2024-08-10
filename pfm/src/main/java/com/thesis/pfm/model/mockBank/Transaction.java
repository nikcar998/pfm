package com.thesis.pfm.model.mockBank;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

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
    private String numeroContoCorrente;
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
    private double importo;
    private String TRN;
    private double commissioni;
    private double totaleOperazione;

    @ManyToOne
    @JoinColumn(name = "account_iban", nullable = false)
    @JsonBackReference
    private Account account;
}
