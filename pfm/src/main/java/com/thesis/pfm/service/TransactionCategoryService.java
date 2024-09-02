package com.thesis.pfm.service;

import com.thesis.pfm.model.Transaction;
import com.thesis.pfm.model.TransactionCategory;
import com.thesis.pfm.repository.TransactionCategoryRepository;
import com.thesis.pfm.repository.mockBank.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TransactionCategoryService {

    @Autowired
    private TransactionCategoryRepository transactionCategoryRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private double calculateCategoryScore(Transaction transaction, TransactionCategory category) {
        double score = 0.0;

        String description = transaction.getDescrizione() != null ? transaction.getDescrizione().toLowerCase() : "";
        BigDecimal amount = transaction.getTotaleOperazione();

        // Farmacia
        if ("Farmacia".equalsIgnoreCase(category.getName())) {
            if (description.contains("farmacia") && amount.compareTo(BigDecimal.ZERO) < 0) {
                score += 1.0;
            }
        }

        // Viaggi e vacanze
        if ("Viaggi e vacanze".equalsIgnoreCase(category.getName())) {
            if (description.contains("albergo") && description.contains("ristorante") && amount.compareTo(BigDecimal.ZERO) < 0) {
                score += 0.5;
            }
        }

        // Bonifici in uscita
        if ("Bonifici in uscita".equalsIgnoreCase(category.getName())) {
            if (description.contains("bonifico") && amount.compareTo(BigDecimal.ZERO) < 0) {
                score += 1.0;
            }
        }

        // Bonifici ricevuti
        if ("Bonifici ricevuti".equalsIgnoreCase(category.getName())) {
            if (description.contains("bonifico") && amount.compareTo(BigDecimal.ZERO) > 0) {
                score += 1.0;
            }
        }

        // Assicurazione auto e moto e tasse veicoli
        if ("Assicurazione auto e moto e tasse veicoli".equalsIgnoreCase(category.getName())) {
            if (description.contains("assicurazione") && (description.contains("auto") || description.contains("moto"))) {
                score += 1.0;
            }
        }

        // Generi alimentari e supermercato
        if ("Generi alimentari e supermercato".equalsIgnoreCase(category.getName())) {
            if (description.contains("supermercato") || description.contains("alimentari")) {
                score += 1.0;
            }
        }

        // Affitti incassati
        if ("Affitti incassati".equalsIgnoreCase(category.getName())) {
            if (description.contains("affitto") && amount.compareTo(BigDecimal.ZERO) > 0) {
                score += 1.0;
            }
        }

        // Pagamento affitti
        if ("Pagamento affitti".equalsIgnoreCase(category.getName())) {
            if (description.contains("affitto") && amount.compareTo(BigDecimal.ZERO) < 0) {
                score += 1.0;
            }
        }

        // Polizze
        if ("Polizze".equalsIgnoreCase(category.getName())) {
            if (description.contains("polizza") || description.contains("assicurazione")) {
                score += 1.0;
            }
        }

        // Carburanti
        if ("Carburanti".equalsIgnoreCase(category.getName())) {
            if (description.contains("carburante") || description.contains("benzina") || description.contains("diesel")) {
                score += 1.0;
            }
        }

        // Gas & energia elettrica
        if ("Gas & energia elettrica".equalsIgnoreCase(category.getName())) {
            if ((description.contains("gas") || description.contains("energia")) && description.contains("elettrica")) {
                score += 1.0;
            }
        }

        // Trasporti, noleggi, taxi e parcheggi
        if ("Trasporti, noleggi, taxi e parcheggi".equalsIgnoreCase(category.getName())) {
            if (description.contains("taxi") || description.contains("noleggio") || description.contains("parcheggio")) {
                score += 1.0;
            }
        }

        // Stipendi e pensioni
        if ("Stipendi e pensioni".equalsIgnoreCase(category.getName())) {
            if ((description.contains("stipendio") || description.contains("pensione")) && amount.compareTo(BigDecimal.ZERO) > 0) {
                score += 1.0;
            }
        }

        // Imposte sul reddito e tasse varie
        if ("Imposte sul reddito e tasse varie".equalsIgnoreCase(category.getName())) {
            if (description.contains("imposte") || description.contains("tasse") || description.contains("reddito")) {
                score += 1.0;
            }
        }

        // Manutenzione casa
        if ("Manutenzione casa".equalsIgnoreCase(category.getName())) {
            if (description.contains("manutenzione") && description.contains("casa")) {
                score += 1.0;
            }
        }

        // Multe
        if ("Multe".equalsIgnoreCase(category.getName())) {
            if (description.contains("multa") || description.contains("contravvenzione")) {
                score += 1.0;
            }
        }

        // Libri, film e musica
        if ("Libri, film e musica".equalsIgnoreCase(category.getName())) {
            if (description.contains("libri") || description.contains("film") || description.contains("musica")) {
                score += 1.0;
            }
        }

        // Donazioni
        if ("Donazioni".equalsIgnoreCase(category.getName())) {
            if (description.contains("donazione") || description.contains("beneficenza")) {
                score += 1.0;
            }
        }

        // Tempo libero varie
        if ("Tempo libero varie".equalsIgnoreCase(category.getName())) {
            if (description.contains("tempo libero") || description.contains("hobby")) {
                score += 1.0;
            }
        }

        // Associazioni
        if ("Associazioni".equalsIgnoreCase(category.getName())) {
            if (description.contains("associazione")) {
                score += 1.0;
            }
        }

        // Ricarica carte
        if ("Ricarica carte".equalsIgnoreCase(category.getName())) {
            if (description.contains("ricarica carta") || description.contains("ricarica")) {
                score += 1.0;
            }
        }

        // Prestiti
        if ("Prestiti".equalsIgnoreCase(category.getName())) {
            if (description.contains("prestito") || description.contains("finanziamento")) {
                score += 1.0;
            }
        }

        // Disinvestimenti, BDR e XME Salvadanaio
        if ("Disinvestimenti, BDR e XME Salvadanaio".equalsIgnoreCase(category.getName())) {
            if (description.contains("disinvestimento") || description.contains("bdr") || description.contains("xme salvadanaio")) {
                score += 1.0;
            }
        }

        // Acqua
        if ("Acqua".equalsIgnoreCase(category.getName())) {
            if (description.contains("acqua")) {
                score += 1.0;
            }
        }

        // Addebiti vari
        if ("Addebiti vari".equalsIgnoreCase(category.getName())) {
            if (description.contains("addebito")) {
                score += 1.0;
            }
        }

        // Alimenti coniuge e figli
        if ("Alimenti coniuge e figli".equalsIgnoreCase(category.getName())) {
            if (description.contains("alimenti") && (description.contains("coniuge") || description.contains("figli"))) {
                score += 1.0;
            }
        }

        // Rimborsi spese mediche
        if ("Rimborsi spese mediche".equalsIgnoreCase(category.getName())) {
            if (description.contains("rimborso") && description.contains("spese mediche")) {
                score += 0.8;
            }
        }

        // Corsi e sport
        if ("Corsi e sport".equalsIgnoreCase(category.getName())) {
            if (description.contains("corso") || description.contains("sport")) {
                score += 0.5;
            }
        }

        // TV, Internet, telefono
        if ("TV, Internet, telefono".equalsIgnoreCase(category.getName())) {
            if (description.contains("tv") || description.contains("internet") || description.contains("telefono")) {
                score += 0.75;
            }
        }

        // Trasporti varie
        if ("Trasporti varie".equalsIgnoreCase(category.getName())) {
            if (description.contains("trasporto")) {
                score += 0.9;
            }
        }

        // Storni
        if ("Storni".equalsIgnoreCase(category.getName())) {
            if (description.contains("storno")) {
                score += 0.7;
            }
        }

        // Regali
        if ("Regali".equalsIgnoreCase(category.getName())) {
            if (description.contains("regalo") || description.contains("donazione")) {
                score += 0.6;
            }
        }

        // Regali ricevuti
        if ("Regali ricevuti".equalsIgnoreCase(category.getName())) {
            if (description.contains("regalo ricevuto") || description.contains("donazione")) {
                score += 0.6;
            }
        }

        // Stipendi e pensioni
        if ("Stipendi e pensioni".equalsIgnoreCase(category.getName())) {
            if (description.contains("stipendio") || description.contains("pensione")) {
                score += 1.0;
            }
        }

        // Rate prestiti
        if("Rate prestiti".equalsIgnoreCase(category.getName())) {
            if (description.contains("rate") && description.contains("prestito")) {
                score += 0.8;
            }
        }

        // Salute e benessere
        if ("Salute e benessere".equalsIgnoreCase(category.getName())) {
            if (description.contains("salute") || description.contains("benessere")) {
                score += 1.0;
            }
        }

        // Trasferimento fondi
        if ("Trasferimento fondi".equalsIgnoreCase(category.getName())) {
            if (description.contains("trasferimento") && description.contains("fondi")) {
                score += 1.0;
            }
        }

        // Spese scolastiche
        if ("Spese scolastiche".equalsIgnoreCase(category.getName())) {
            if (description.contains("scuola") || description.contains("scolastico")) {
                score += 0.9;
            }
        }

        // Abbonamenti e servizi digitali
        if ("Abbonamenti e servizi digitali".equalsIgnoreCase(category.getName())) {
            if (description.contains("abbonamento") || description.contains("servizio digitale")) {
                score += 0.85;
            }
        }

        // Arredamento e casalinghi
        if ("Arredamento e casalinghi".equalsIgnoreCase(category.getName())) {
            if (description.contains("arredamento") || description.contains("casalinghi")) {
                score += 1.0;
            }
        }

        // Riparazione veicoli
        if ("Riparazione veicoli".equalsIgnoreCase(category.getName())) {
            if (description.contains("riparazione") && (description.contains("auto") || description.contains("moto"))) {
                score += 1.0;
            }
        }

        // Giardinaggio
        if ("Giardinaggio".equalsIgnoreCase(category.getName())) {
            if (description.contains("giardino") || description.contains("giardinaggio")) {
                score += 0.7;
            }
        }

        // Abbigliamento e accessori
        if ("Abbigliamento e accessori".equalsIgnoreCase(category.getName())) {
            if (description.contains("abbigliamento") || description.contains("accessori")) {
                score += 0.95;
            }
        }

        // Farmaci
        if ("Farmaci".equalsIgnoreCase(category.getName())) {
            if (description.contains("farmaci")) {
                score += 1.0;
            }
        }

        // Spese funerarie
        if ("Spese funerarie".equalsIgnoreCase(category.getName())) {
            if (description.contains("funerario") || description.contains("funerale")) {
                score += 0.8;
            }
        }

        // Intrattenimento
        if ("Intrattenimento".equalsIgnoreCase(category.getName())) {
            if (description.contains("cinema") || description.contains("teatro") || description.contains("concerti")) {
                score += 1.0;
            }
        }

        // Alimenti e bevande
        if ("Alimenti e bevande".equalsIgnoreCase(category.getName())) {
            if (description.contains("alimenti") || description.contains("bevande")) {
                score += 1.0;
            }
        }

        // Gadget tecnologici
        if ("Gadget tecnologici".equalsIgnoreCase(category.getName())) {
            if (description.contains("gadget") || description.contains("tecnologico")) {
                score += 0.9;
            }
        }

        // Ristrutturazioni
        if ("Ristrutturazioni".equalsIgnoreCase(category.getName())) {
            if (description.contains("ristrutturazione") || description.contains("lavori")) {
                score += 0.85;
            }
        }

        // Eventi speciali
        if ("Eventi speciali".equalsIgnoreCase(category.getName())) {
            if (description.contains("matrimonio") || description.contains("festa")) {
                score += 0.8;
            }
        }

        // Cure termali
        if ("Cure termali".equalsIgnoreCase(category.getName())) {
            if (description.contains("cure termali") || description.contains("terme")) {
                score += 1.0;
            }
        }

        // Sport
        if ("Sport".equalsIgnoreCase(category.getName())) {
            if (description.contains("sport")) {
                score += 1.0;
            }
        }

        // Viaggi di lavoro
        if ("Viaggi di lavoro".equalsIgnoreCase(category.getName())) {
            if (description.contains("viaggio") && description.contains("lavoro")) {
                score += 0.9;
            }
        }

        // Animali domestici
        if ("Animali domestici".equalsIgnoreCase(category.getName())) {
            if (description.contains("animali") || description.contains("domestici")) {
                score += 0.7;
            }
        }

        // Studio e formazione
        if ("Studio e formazione".equalsIgnoreCase(category.getName())) {
            if (description.contains("studio") || description.contains("formazione")) {
                score += 0.95;
            }
        }

        return score;
    }


    // Metodo per categorizzare una lista di transazioni
    public void categorizeTransactions(List<Transaction> transactions) {
        List<TransactionCategory> categories = transactionCategoryRepository.findAll();

        for (Transaction transaction : transactions) {
            TransactionCategory bestCategory = null;
            double highestScore = 0.0;

            for (TransactionCategory category : categories) {
                double score = calculateCategoryScore(transaction, category);

                if (score > highestScore) {
                    highestScore = score;
                    bestCategory = category;
                }
            }

            if (bestCategory != null) {
                transaction.setTransactionCategory(bestCategory);
                transactionRepository.save(transaction);
            }
        }
    }
}
