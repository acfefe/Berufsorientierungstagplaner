package org.example.controller;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.gui.SchuelerPanel;
import org.example.models.SchuelerList;

@Data
@AllArgsConstructor
public class SchuelerController {

    private SchuelerList schuelerList;
    private SchuelerPanel schuelerPanel;
}
