package com.example.comissaoapi.carModels.api;

import com.example.comissaoapi.carModels.dto.CarModelDTO;
import com.example.comissaoapi.carModels.dto.FileDTO;
import com.example.comissaoapi.carModels.facade.CarModelFacade;
import com.example.comissaoapi.useful.Base64FileReader;
import com.example.comissaoapi.useful.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/carModel", produces = MediaType.APPLICATION_JSON_VALUE)
public class CarModelAPI {
    @Autowired
    private CarModelFacade carModelFacade;
    private static final Logger logger = LogManager.getLogger(CarModelAPI.class);

    @PostMapping
    @ResponseBody
    public List<CarModelDTO> create(@RequestBody FileDTO fileDTO) {
        List<CarModelDTO> carModelDTOList = new ArrayList<>();
        try {
            Base64FileReader reader = new Base64FileReader(fileDTO.getFileBase64(), fileDTO.getFileType());
            Object[] fileContents = reader.getFileContents();

            if (fileDTO.getFileType().equalsIgnoreCase("pdf")) {
                String[][] pdfContents = (String[][]) fileContents;
                for (int pageIndex = 0; pageIndex < pdfContents.length; pageIndex++) {
                    String[] page = pdfContents[pageIndex];
                    for (int lineIndex = 0; lineIndex < page.length; lineIndex++) {
                        String line = page[lineIndex];
                        CarModelDTO carModelDTO = new CarModelDTO();
                        // Parse line and set values in carModelDTO
                        carModelDTOList.add(carModelFacade.criar(carModelDTO));
                    }
                }
            } else if (fileDTO.getFileType().equalsIgnoreCase("xlsx")) {
                String[][][] excelContents = (String[][][]) fileContents;
                for (int sheetIndex = 0; sheetIndex < excelContents.length; sheetIndex++) {
                    String[][] sheet = excelContents[sheetIndex];
                    String model = "";
                    boolean preco_alc_exist = false;
                    boolean preco_ao_exist = false;
                    for (int rowIndex = 0; rowIndex < sheet.length; rowIndex++) {
                        for (int i = 0; i < sheet[rowIndex].length; i++) {
                            sheet[rowIndex][i] = sheet[rowIndex][i].replaceAll("\n", "\s");
                        }
                        if (sheetIndex == 0) {
                            if (
                                rowIndex < 8 ||
                                ArrayUtils.in_array(rowIndex, new int[]{9,10,12})
                            ) continue;
                            if (rowIndex == 8) {
                                model = sheet[rowIndex][0];
                                continue;
                            }
                            if (rowIndex == 11) {
                                if (
                                    ArrayUtils.indexExists(sheet[rowIndex], 7) &&
                                    sheet[rowIndex][7].contains("PREÇO")
                                ) {
                                    preco_alc_exist = true;
                                }
                                if (ArrayUtils.indexExists(sheet[rowIndex], 9)) {
                                    preco_ao_exist = true;
                                }
                            }
                        }
                        String[] row = sheet[rowIndex];
                        CarModelDTO carModelDTO = new CarModelDTO();
                        carModelDTO.setModel(model);

                        carModelDTOList.add(carModelFacade.criar(carModelDTO));
                    }
                }
            }

        } catch (IOException e) {
            logger.debug(e);
        }
        return carModelDTOList;
    }

    @PutMapping("/{carModelId}")
    @ResponseBody
    public CarModelDTO update(@PathVariable("carModelId") Long carModelId, @RequestBody CarModelDTO carModelDTO) {
        return carModelFacade.atualizar(carModelDTO, carModelId);
    }

    @GetMapping
    @ResponseBody
    public List<CarModelDTO> getAll() {
        return carModelFacade.getAll();
    }

    @DeleteMapping("/{carModelId}")
    @ResponseBody
    public String delete(@PathVariable("carModelId") Long carModelId) {
        return carModelFacade.delete(carModelId);
    }
}
