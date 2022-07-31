package com.cic.service.data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import com.cic.openapi.model.Match;
import com.cic.openapi.model.Person;
import com.cic.service.person.PersonKeys;

public class DataService {

  public File createFile(Match[] matches) {
    final File csvFile = new File("src/main/resources/tempFileForDownload.csv");
    try (OutputStream os = new FileOutputStream(csvFile)) {
      final String overallHeader = tableNameHeaders("Mentee") + tableNameHeaders("Mentor");
      os.write((overallHeader + "\n").getBytes());
      final String headers = writeHeaders();
      os.write((headers + "\n").getBytes());
      for (Match match : matches) {
        final String menteeToCsv = writePersonToCsv(match.getMentee());
        final String mentorToCsv = writePersonToCsv(match.getMentor());
        final String joinToRecords = menteeToCsv + "," + mentorToCsv;
        os.write((joinToRecords + "\n").getBytes());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return csvFile;
  }

  private String writePersonToCsv(final Person person) {
    final List<String> personToStringArray = new ArrayList<>();
    for (PersonKeys personKey : PersonKeys.values()) {
      switch (personKey) {
        case gender:
          personToStringArray.add(person.getGender().toString());
          break;
        case keyWords:
          if (person.getKeywords() != null) {
            personToStringArray.add(String.join("|", person.getKeywords()));
          } else {
            personToStringArray.add("No Keywords");
          }
          break;
        case emailAddress:
          personToStringArray.add(person.getEmailAddress());
          break;
        case genderPreference:
          personToStringArray.add(person.getGenderPreference().toString());
          break;
        case name:
          personToStringArray.add(person.getName());
          break;
        default:
          break;
      }
    }
    return String.join(",", personToStringArray);
  }

  private String tableNameHeaders(final String tableName) {
    final String repeatedCommas =
        new String(new char[PersonKeys.values().length]).replace("\0", ",");
    return tableName + repeatedCommas;
  }

  private String writeHeaders() {
    final List<String> headerRow = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      for (PersonKeys personKey : PersonKeys.values()) {
        headerRow.add(personKey.toString());
      }
    }
    return String.join(",", headerRow);
  }
}
