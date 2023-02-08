package com.rahulshettyacademy;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelDataDriver {

    public static Object[][] getData(String fileName, String worksheet) throws IOException {

        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\resources\\" + fileName + ".xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        XSSFSheet sheet = workbook.getSheet(worksheet);

        Iterator<Row> rowIter = sheet.iterator();

        ArrayList<List<String>> cellValuesList = new ArrayList<>();

        int lastRowNum = sheet.getLastRowNum();
        LinkedHashSet<Integer> randAisleSet = generateUniqueRandNumberSet(lastRowNum);
        Iterator<Integer> randIsleGenerator = randAisleSet.iterator();

        rowIter.next();
        while(rowIter.hasNext()) {
            Row nextRow = rowIter.next();
            Iterator<Cell> cellIter = nextRow.cellIterator();
            ArrayList<String> rowValues = new ArrayList<>();

            while (cellIter.hasNext()) {
                Cell nextCell = cellIter.next();
                String cellVal = nextCell.getStringCellValue();
                if (cellVal.equals("RandAisle")) {
                    Integer randAisle = randIsleGenerator.next();
                    cellVal = String.valueOf(randAisle);
                }
                rowValues.add(cellVal);
            }
            cellValuesList.add(rowValues);
        }

        String[][] multiArr = new String[cellValuesList.size()][];

        for (int i = 0; i < cellValuesList.size(); i++) {
            String[] newArr = new String[cellValuesList.get(i).size()];
            multiArr[i] = cellValuesList.get(i).toArray(newArr);
        }

        return multiArr;
    }

    private static LinkedHashSet<Integer> generateUniqueRandNumberSet(int numCount){
        LinkedHashSet<Integer> set = new LinkedHashSet<>();

        while(set.size() < numCount)
        {
            set.add(new Random().nextInt());
        }

        return set;
    }
}
