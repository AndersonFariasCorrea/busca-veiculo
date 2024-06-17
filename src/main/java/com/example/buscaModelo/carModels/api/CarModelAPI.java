package com.example.buscaModelo.carModels.api;

import com.example.buscaModelo.carModels.dto.CarModelDTO;
import com.example.buscaModelo.carModels.dto.FileDTO;
import com.example.buscaModelo.carModels.facade.CarModelFacade;
import com.example.buscaModelo.useful.Base64FileReader;
import com.example.buscaModelo.useful.ArrayUtils;
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
                        // String line = page[lineIndex];
                        // CarModelDTO carModelDTO = new CarModelDTO();
                        // carModelDTOList.add(carModelFacade.criar(carModelDTO));
                    }
                }
            } else if (fileDTO.getFileType().equalsIgnoreCase("xlsx")) {
                String[][][] excelContents = (String[][][]) fileContents;
                for (int sheetIndex = 0; sheetIndex < excelContents.length; sheetIndex++) {
                    String[][] sheet = excelContents[sheetIndex];
                    String model = "";
                    int zfmIndex = 0;
                    int nacionalIndex = 0;
                    int aoIndex = 0;
                    int alcIndex = 0;
                    String versao = "";
                    boolean endPage = false;

                    for (int rowIndex = 0; rowIndex < sheet.length; rowIndex++) {
                        if (ArrayUtils.indexExists(sheet[rowIndex], 0) && sheet[rowIndex][0].contains("*Os recursos")) {
                            endPage = true;
                        }
                        if (endPage) continue;

                        for (int i = 0; i < sheet[rowIndex].length; i++) {
                            sheet[rowIndex][i] = sheet[rowIndex][i].replaceAll("\n", " ");
                        }
                        if (sheetIndex == 0) {
                            if (rowIndex < 8 || ArrayUtils.in_array(rowIndex, new int[]{9, 10, 12, 13, 14})) continue;
                            if (rowIndex == 8) {
                                model = sheet[rowIndex][0];
                                continue;
                            }
                            if (rowIndex == 11) {
                                if (ArrayUtils.indexExists(sheet[rowIndex], 4)) {
                                    if(sheet[rowIndex][4].contains("PREÇO NACIONAL")) nacionalIndex = 4;
                                }
                                if (ArrayUtils.indexExists(sheet[rowIndex], 5)) {
                                    if (sheet[rowIndex][5].contains("PREÇO ZFM")) zfmIndex = 5;
                                }
                                if (ArrayUtils.indexExists(sheet[rowIndex], 6)) {
                                    if (sheet[rowIndex][6].contains("PREÇO ZFM")) zfmIndex = 6;
                                    if (sheet[rowIndex][6].contains("PREÇO ALC")) alcIndex = 6;
                                }
                                if (ArrayUtils.indexExists(sheet[rowIndex], 7)) {
                                    if (sheet[rowIndex][7].contains("PREÇO AO")) aoIndex = 7;
                                }
                                continue;
                            }
                        } else {
                            if (rowIndex == 0) {
                                model = sheet[rowIndex][0];
                                continue;
                            }
                            if (ArrayUtils.in_array(rowIndex, new int[]{1,2,4,5,6})) continue;
                            if (rowIndex == 3) {
                                if (ArrayUtils.indexExists(sheet[rowIndex], 4)) {
                                    if (sheet[rowIndex][4].contains("PREÇO NACIONAL")) nacionalIndex = 4;
                                }
                                if (ArrayUtils.indexExists(sheet[rowIndex], 5)) {
                                    if (sheet[rowIndex][5].contains("PREÇO ZFM")) zfmIndex = 5;
                                }
                                if (ArrayUtils.indexExists(sheet[rowIndex], 6)) {
                                    if (sheet[rowIndex][6].contains("PREÇO ZFM")) alcIndex = 6;
                                }
                                if (ArrayUtils.indexExists(sheet[rowIndex], 7)) {
                                    if (sheet[rowIndex][7].contains("PREÇO AO")) aoIndex = 7;
                                }
                                continue;
                            }
                        }

                        CarModelDTO carModelDTO = new CarModelDTO();
                        if (!sheet[rowIndex][0].isEmpty()) versao = sheet[rowIndex][0];
                        carModelDTO.setVersao(versao);
                        carModelDTO.setModel(model);
                        carModelDTO.setConfig(sheet[rowIndex][2]);
                        carModelDTO.setConteudo(sheet[rowIndex][3]);

                        if (zfmIndex != 0 && !sheet[rowIndex][zfmIndex].isEmpty()) {
                            carModelDTO.setPreco_zfm(Float.parseFloat(sheet[rowIndex][zfmIndex]));
                        }
                        if (nacionalIndex != 0 && !sheet[rowIndex][nacionalIndex].isEmpty()) {
                            carModelDTO.setPreco_nacional(Float.parseFloat(sheet[rowIndex][nacionalIndex]));
                        }
                        if (aoIndex != 0 && !sheet[rowIndex][aoIndex].isEmpty()) {
                            carModelDTO.setPreco_ao(Float.parseFloat(sheet[rowIndex][aoIndex]));
                        }
                        if (alcIndex != 0 && !sheet[rowIndex][alcIndex].isEmpty()) {
                            carModelDTO.setPreco_alc(Float.parseFloat(sheet[rowIndex][alcIndex]));
                        }

                        carModelDTOList.add(carModelFacade.criar(carModelDTO));
                    }
                    zfmIndex = 0;
                    nacionalIndex = 0;
                    aoIndex = 0;
                    alcIndex = 0;
                }
            }

        } catch (IOException e) {
            logger.debug(e);
        } catch (NumberFormatException e) {
            logger.error("Number format exception: " + e.getMessage(), e);
        }
        return carModelDTOList;
    }

    @PutMapping("/{carModelId}")
    @ResponseBody
    public CarModelDTO update(@PathVariable("carModelId") Long carModelId, @RequestBody CarModelDTO carModelDTO) {
        carModelFacade.atualizar(carModelDTO, carModelId);
        return carModelDTO;
    }

    @GetMapping
    @ResponseBody
    public List<CarModelDTO> getAll() {
        return carModelFacade.getAll();
    }

    @GetMapping("/{carModelId}")
    @ResponseBody
    public CarModelDTO getById(@PathVariable Long carModelId) {
        return carModelFacade.getById(carModelId);
    }

    @GetMapping("/model/{model}")
    @ResponseBody
    public List<CarModelDTO> getByModel(@PathVariable String model) {
        return carModelFacade.getByModel(model);
    }

    @DeleteMapping("/{carModelId}")
    @ResponseBody
    public String delete(@PathVariable("carModelId") Long carModelId) {
        return carModelFacade.delete(carModelId);
    }
}
