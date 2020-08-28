package baseclass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileZip {
  String timestamp = new SimpleDateFormat("yyyy_MM_dd__hh_mm_ss")
      .format(new Date());
  String sourceFolder;
  String outputZipFile;
  List<String> fileList;

  public FileZip() {
    fileList = new ArrayList<String>();
  }

  /**
   * get the zip file by providing the source and destination folder.
   * 
   * @param sourceFolder      sourceFolder
   * @param destinationFolder destinationFolder
   * @param zipExtension      zipExtension
   * @return
   */
  public String getZipFile(String sourceFolder, String destinationFolder,
      String zipName, String zipExtension) {
    outputZipFile = destinationFolder + "\\" + zipName + "_" + timestamp + "."
        + zipExtension;
    // "E:\\MyFile.zip";
    this.sourceFolder = sourceFolder;
    // "E:\\TestResultsRepository";
    generateFileList(new File(sourceFolder));
    zipIt(outputZipFile);
    return outputZipFile;
  }

  /**
   * Zip it.
   * 
   * @param zipFile output ZIP file location
   */
  private void zipIt(String zipFile) {
    byte[] buffer = new byte[1024];
    try {
      FileOutputStream fos = new FileOutputStream(zipFile);
      ZipOutputStream zos = new ZipOutputStream(fos);
      System.out.println("Output to Zip : " + zipFile);
      for (String file : this.fileList) {
        System.out.println("File Added : " + file);
        ZipEntry ze = new ZipEntry(file);
        zos.putNextEntry(ze);
        FileInputStream in = new FileInputStream(
            sourceFolder + File.separator + file);
        int len;
        while ((len = in.read(buffer)) > 0) {
          zos.write(buffer, 0, len);
        }
        in.close();
      }
      zos.closeEntry();
      /*
       * remember to close it
       */
      zos.close();
      System.out.println("Done");
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  /**
   * Traverse a directory and get all files, and add the file into fileList.
   * 
   * @param node file or directory
   */
  private void generateFileList(File node) {
    // add file only
    if (node.isFile()) {
      fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
    }
    if (node.isDirectory()) {
      String[] subNote = node.list();
      if (subNote != null) {
        for (String filename : subNote) {
          generateFileList(new File(node, filename));
        }
      }
    }
  }

  /**
   * Format the file path for zip.
   * 
   * @param file file path
   * @return Formatted file path
   */
  private String generateZipEntry(String file) {
    return file.substring(sourceFolder.length() + 1, file.length());
  }
}