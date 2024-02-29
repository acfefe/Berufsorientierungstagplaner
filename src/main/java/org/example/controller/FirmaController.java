package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.gui.FirmaPanel;
import org.example.models.FirmaList;

@Data
@AllArgsConstructor
public class FirmaController {

    private FirmaList firmaList;
    private FirmaPanel firmaPanel;

}
