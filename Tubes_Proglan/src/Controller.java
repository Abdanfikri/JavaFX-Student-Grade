//package com.example.farah;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;

import java.io.*;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.Initializable;

/**
 * @author abdan, fara, rayyan
 * @apiNote javafx version of JDK 21
 */
public class Controller implements Initializable {

    @FXML
    private TableView<Mahasiswa> MyTable;

    @FXML
    private TextField TextMataKuliah;

    @FXML
    private TextField TextNama;

    @FXML
    private TextField TextTugas1;

    @FXML
    private TextField TextTugas2;

    @FXML
    private TextField TextTugas3;

    @FXML
    private TextField TextTugas4;

    @FXML
    private TextField TextTugas5;

    @FXML
    private TextField TextTugas6;

    @FXML
    private TextField TextTugas7;

    @FXML
    private TextField TextUAS;

    @FXML
    private TextField TextUTS;

    @FXML
    private TableColumn<Mahasiswa, String> colMataKuliah;

    @FXML
    private TableColumn<Mahasiswa, String> colNama;

    @FXML
    private TableColumn<Mahasiswa, String> colNilai;

    @FXML
    private TableColumn<Mahasiswa, String> colNilaiAkhir;

    @FXML
    private TableColumn<Mahasiswa, String> colTugas1;

    @FXML
    private TableColumn<Mahasiswa, String> colTugas2;

    @FXML
    private TableColumn<Mahasiswa, String> colTugas3;

    @FXML
    private TableColumn<Mahasiswa, String> colTugas4;

    @FXML
    private TableColumn<Mahasiswa, String> colTugas5;

    @FXML
    private TableColumn<Mahasiswa, String> colTugas6;

    @FXML
    private TableColumn<Mahasiswa, String> colTugas7;

    @FXML
    private TableColumn<Mahasiswa, String> colUAS;

    @FXML
    private TableColumn<Mahasiswa, String> colUTS;

    @FXML
    private Button myadd;

    @FXML
    private Button ButtonDelete;
    private final ObservableList<Mahasiswa> tableData = FXCollections.observableArrayList();
    private final String FILE_PATH = "C:\\Users\\user\\IdeaProjects\\Tubes_Proglan\\src\\dataMahasiswa.txt";

    /**
     * addData digunakan untuk memasukan data ke table view berdasarkan user input
     *
     * @param event ActionEvent sesuai oleh pengguna.
     */
    @FXML
    void addData(ActionEvent event) {
        String Nama = TextNama.getText();
        String MataKuliah = TextMataKuliah.getText();
        String Tugas1 = TextTugas1.getText();
        String Tugas2 = TextTugas2.getText();
        String Tugas3 = TextTugas3.getText();
        String Tugas4 = TextTugas4.getText();
        String Tugas5 = TextTugas5.getText();
        String Tugas6 = TextTugas6.getText();
        String Tugas7 = TextTugas7.getText();
        String UAS = TextUAS.getText();
        String UTS = TextUTS.getText();

        saveDataToFile();

        if (isInputValid(Tugas1, Tugas2, Tugas3, Tugas4, Tugas5, Tugas6, Tugas7, UAS, UTS)) {
            if (!isAlphaWithSpace(Nama) || !isAlphaWithSpace(MataKuliah)) {
                showAlert("Invalid input", "Masukan huruf untuk kolom nama dan mata kuliah");
            } else {
                // Jika semua input valid, lanjutkan dengan menambahkan data
                try {
                    double parsedTugas1 = Double.parseDouble(Tugas1);
                    double parsedTugas2 = Double.parseDouble(Tugas2);
                    double parsedTugas3 = Double.parseDouble(Tugas3);
                    double parsedTugas4 = Double.parseDouble(Tugas4);
                    double parsedTugas5 = Double.parseDouble(Tugas5);
                    double parsedTugas6 = Double.parseDouble(Tugas6);
                    double parsedTugas7 = Double.parseDouble(Tugas7);
                    double parsedUAS = Double.parseDouble(UAS);
                    double parsedUTS = Double.parseDouble(UTS);

                    // meriksa apakah nilainya berada dalam rentang yang valid (1-100)
                    if (isInRange(parsedTugas1, parsedTugas2, parsedTugas3, parsedTugas4, parsedTugas5,
                            parsedTugas6, parsedTugas7, parsedUAS, parsedUTS)) {

                        Mahasiswa mahasiswa = new Mahasiswa(Nama, MataKuliah, Tugas1, Tugas2, Tugas3,
                                Tugas4, Tugas5, Tugas6, Tugas7, UAS, UTS);
                        tableData.add(mahasiswa);
                        MyTable.setItems(tableData);

                        // Clear the text fields after adding data
                        clearInputFields();
                    } else {
                        showAlert("Invalid input", "Masukan nilai 1 - 100");
                    }
                } catch (NumberFormatException e) {
                    showAlert("Invalid input", "masukan angka untuk tugas, uts dan uas.");
                }

            }
        } else {
            showAlert("Invalid input", "Silakan isi semua kolom.");
        }
        saveDataToFile();
    }



    /**
     * DeleteData Menghapus data yang dipilih dari TableView.
     *
     * @param event ActionEvent dipicu oleh pengguna
     */
    @FXML
    void DeleteData(ActionEvent event) {
        Mahasiswa select = MyTable.getSelectionModel().getSelectedItem();
        if (select != null) {
            tableData.remove(select);
            MyTable.getSelectionModel().clearSelection();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText("Pilih Tabel yang ingin di Hapus");
            alert.show();
        }
        saveDataToFile();
    }


    /**
     * method isInputValid digunakan untuk Memvalidasi bahwa semua kolom input tidak kosong.
     *
     * @param inputs inputan dari tugas, uts dan uas.
     * @return True kalau semua masukan tidak kosong, kalau ada yg kosong false.
     */
    public static boolean isInputValid(String... inputs) {

        for (String input : inputs) {
            if (input.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * method isAlphaWithSpace Memvalidasi apakah string yang diberikan terdiri dari huruf.
     *
     * @param input String masukan yang akan divalidasi.
     * @return True kalau input berupa angka kalau ada yg kosong atau huruf  false.
     */
    public static boolean isAlphaWithSpace(String input) {
        return input.matches("^[a-zA-Z]+(\\s[a-zA-Z]+)?$");
    }

    /**
     * method isInRange digunakan untuk Memvalidasi apakah nilai tugas,uts dan uas  rentang 0 hingga 100 .
     *
     * @param values Array double yang akan divalidasi.
     * @return true jika semua nilai berada dalam rentang yang valid; jika tidak, false.
     */
    public static boolean isInRange(double... values) {
        for (double value : values) {
            if (value < 0 || value > 100) {
                return false;
            }
        }
        return true;
    }

    /**
     * method shownAlert Menampilkan peringatan dengan header dan konten yang ditentukan.
     *
     * @param header  The header text of the alert.
     * @param content The content text of the alert.
     */
    private void showAlert(String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.show();
    }

    //method clearInputFields digunakan untuk menghapus nilai di text field
    private void clearInputFields() {
        TextNama.clear();
        TextMataKuliah.clear();
        TextTugas1.clear();
        TextTugas2.clear();
        TextTugas3.clear();
        TextTugas4.clear();
        TextTugas5.clear();
        TextTugas6.clear();
        TextTugas7.clear();
        TextUAS.clear();
        TextUTS.clear();
    }

    //digunakan untuk safe data mahasiswa ke file txt
    private void saveDataToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Mahasiswa mahasiswa : tableData) {
                writer.println(
                        mahasiswa.getNama() + "," +
                                mahasiswa.getmataKuliah() + "," +
                                mahasiswa.getTugas1() + "," +
                                mahasiswa.getTugas2() + "," +
                                mahasiswa.getTugas3() + "," +
                                mahasiswa.getTugas4() + "," +
                                mahasiswa.getTugas5() + "," +
                                mahasiswa.getTugas6() + "," +
                                mahasiswa.getTugas7() + "," +
                                mahasiswa.getUas() + "," +
                                mahasiswa.getUts()
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //digunakan untuk membaca data dan memasukan data di file ke table data
    private void loadDataFromFile() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] data = line.split(",");
                    if (data.length == 11) {
                        Mahasiswa mahasiswa = new Mahasiswa(
                                data[0], data[1], data[2], data[3], data[4],
                                data[5], data[6], data[7], data[8], data[9], data[10]
                        );
                        tableData.add(mahasiswa);
                    }
                }
                MyTable.setItems(tableData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colMataKuliah.setCellValueFactory(new PropertyValueFactory<>("mataKuliah"));
        colTugas1.setCellValueFactory(new PropertyValueFactory<>("tugas1"));
        colTugas2.setCellValueFactory(new PropertyValueFactory<>("tugas2"));
        colTugas3.setCellValueFactory(new PropertyValueFactory<>("tugas3"));
        colTugas4.setCellValueFactory(new PropertyValueFactory<>("tugas4"));
        colTugas5.setCellValueFactory(new PropertyValueFactory<>("tugas5"));
        colTugas6.setCellValueFactory(new PropertyValueFactory<>("tugas6"));
        colTugas7.setCellValueFactory(new PropertyValueFactory<>("tugas7"));
        colUTS.setCellValueFactory(new PropertyValueFactory<>("uts"));
        colUAS.setCellValueFactory(new PropertyValueFactory<>("uas"));


        loadDataFromFile();

        colNilai.setCellValueFactory(cellData -> {
            Mahasiswa mahasiswa = cellData.getValue();
            double nilai = calculateNilai(mahasiswa);
            return new SimpleStringProperty(String.format("%.2f", nilai));
        });

        colNilaiAkhir.setCellValueFactory(cellData -> {
            Mahasiswa mahasiswa = cellData.getValue();
            double nilai = calculateNilai(mahasiswa);

            String nilaiAkhir;
            if (nilai > 80) {
                nilaiAkhir = "A";
            } else if (nilai > 75) {
                nilaiAkhir = "B+";
            } else if (nilai > 70) {
                nilaiAkhir = "B";
            } else if (nilai > 60) {
                nilaiAkhir = "C+";
            } else if (nilai > 50) {
                nilaiAkhir = "C";
            } else if (nilai > 40) {
                nilaiAkhir = "D";
            } else if (nilai >= 0) {
                nilaiAkhir = "E";
            } else {
                nilaiAkhir = "Tidak Lulus";
            }

            return new SimpleStringProperty(nilaiAkhir);
        });


    }

    /**
     * method calculate Nilai digunakan untuk menghitung nilai dan nilai akhir dari data Mahasiswa.
     *
     * @param mahasiswa The Mahasiswa object for which to calculate the final grade.
     * @return The calculated final grade as a double.
     */

    private double calculateNilai(Mahasiswa mahasiswa) {
        double tugas1 = Double.parseDouble(mahasiswa.getTugas1()) * 0.05;
        double tugas2 = Double.parseDouble(mahasiswa.getTugas2()) * 0.05;
        double tugas3 = Double.parseDouble(mahasiswa.getTugas3()) * 0.06;
        double tugas4 = Double.parseDouble(mahasiswa.getTugas4()) * 0.06;
        double tugas5 = Double.parseDouble(mahasiswa.getTugas5()) * 0.06;
        double tugas6 = Double.parseDouble(mahasiswa.getTugas6()) * 0.06;
        double tugas7 = Double.parseDouble(mahasiswa.getTugas7()) * 0.06;
        double uts = Double.parseDouble(mahasiswa.getUts()) * 0.3;
        double uas = Double.parseDouble(mahasiswa.getUas()) * 0.3;

        return tugas1 + tugas2 + tugas3 + tugas4 + tugas5 + tugas6 + tugas7 + uts + uas;
    }



    public class Mahasiswa {
        private final StringProperty nama;
        private final StringProperty mataKuliah;
        private final StringProperty tugas1;
        private final StringProperty tugas2;
        private final StringProperty tugas3;
        private final StringProperty tugas4;
        private final StringProperty tugas5;
        private final StringProperty tugas6;
        private final StringProperty tugas7;
        private final StringProperty uas;
        private final StringProperty uts;

        private Mahasiswa(String nama, String mataKuliah, String tugas1, String tugas2, String tugas3, String tugas4,
                          String tugas5, String tugas6, String tugas7, String uas, String uts) {
            this.nama = new SimpleStringProperty(nama);
            this.mataKuliah = new SimpleStringProperty(mataKuliah);
            this.tugas1 = new SimpleStringProperty(tugas1);
            this.tugas2 = new SimpleStringProperty(tugas2);
            this.tugas3 = new SimpleStringProperty(tugas3);
            this.tugas4 = new SimpleStringProperty(tugas4);
            this.tugas5 = new SimpleStringProperty(tugas5);
            this.tugas6 = new SimpleStringProperty(tugas6);
            this.tugas7 = new SimpleStringProperty(tugas7);
            this.uas = new SimpleStringProperty(uas);
            this.uts = new SimpleStringProperty(uts);
        }

        public StringProperty namaProperty() {
            return nama;
        }

        public StringProperty mataKuliahProperty() {
            return mataKuliah;
        }

        public StringProperty tugas1Property() {
            return tugas1;
        }

        public StringProperty tugas2Property() {
            return tugas2;
        }

        public StringProperty tugas3Property() {
            return tugas3;
        }

        public StringProperty tugas4Property() {
            return tugas4;
        }

        public StringProperty tugas5Property() {
            return tugas5;
        }

        public StringProperty tugas6Property() {
            return tugas6;
        }

        public StringProperty tugas7Property() {
            return tugas7;
        }

        public StringProperty uasProperty() {
            return uas;
        }

        public StringProperty utsProperty() {
            return uts;
        }

        // Add getters for the properties

        public String getNama() {
            return nama.get();
        }

        public String getmataKuliah() {
            return mataKuliah.get();
        }

        public String getTugas1() {
            return tugas1.get();
        }

        public String getTugas2() {
            return tugas2.get();
        }

        public String getTugas3() {
            return tugas3.get();
        }

        public String getTugas4() {
            return tugas4.get();
        }

        public String getTugas5() {
            return tugas5.get();
        }

        public String getTugas6() {
            return tugas6.get();
        }

        public String getTugas7() {
            return tugas7.get();
        }

        public String getUas() {
            return uas.get();
        }

        public String getUts() {
            return uts.get();
        }


    }


}
