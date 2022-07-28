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
    final File csvFile = new File("");
    try (OutputStream os = new FileOutputStream(csvFile)) {
      String headers = writeHeaders();
      os.write(headers.getBytes());
      for (Match match : matches) {
        final String menteeToCsv = writePersonToCsv(match.getMentee());
        final String mentorToCsv = writePersonToCsv(match.getMentor());
        final String joinToRecords = menteeToCsv + "," + mentorToCsv;
        os.write(joinToRecords.getBytes());
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
          personToStringArray.add(String.join("|", person.getKeywords()));
          break;
        case emailAddress:
          personToStringArray.add(person.getEmailAddress());
          break;
        case genderPreference:
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

  private String writeHeaders() {
    String headerRow = "";
    for (int i = 0; i < 1; i++) {
      for (PersonKeys personKey : PersonKeys.values()) {
        headerRow += "," + personKey.toString();
      }
    }
    headerRow = headerRow.substring(0, headerRow.length() - 1);
    return headerRow;
  }
}
