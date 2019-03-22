package com.fstg.caissev2.controllers.util;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableViewProvider<T> {

    protected List<String> tableViewTitles = new ArrayList();

    protected List<T> tableViewObjects = new ArrayList<>();

    protected TableView<T> table;

    private ObservableList<T> data = FXCollections.observableArrayList(tableViewObjects);

    public TableViewProvider(TableView<T> table,List<String> attributesNames) {
        this.tableViewTitles.addAll(attributesNames);
        this.table = table;
        fillTable();
    }
    public TableViewProvider(TableView<T> table,String... values) {
        this.tableViewTitles.addAll(Arrays.asList(values));
        this.table = table;
        fillTable();
    }
    public TableViewProvider(TableView<T> table, List<T> list,String... values) {
        this.tableViewTitles.addAll(Arrays.asList(values));
        this.table = table;
        this.tableViewObjects = list;
        fillTable();
    }

    public void select(T t){
        table.getSelectionModel().select(t);
    }

    private void fillTable(){
        for (String tableViewTitle : tableViewTitles) {
            TableColumn<T, ?> column = new TableColumn<>(tableViewTitle);
            column.setCellValueFactory(new PropertyValueFactory<>(tableViewTitle));
            table.getColumns().add(column);
        }
        data = FXCollections.observableArrayList(tableViewObjects);
        table.setItems(data);
    }

    public List<T> getList() {
        return tableViewObjects;
    }

    public void setList(List<T> list) {
        this.tableViewObjects = list;
        data = FXCollections.observableArrayList(list);
        table.setItems(data);
    }

    public void addItem(T t){
        tableViewObjects.add(t);
        data = FXCollections.observableArrayList(tableViewObjects);
        table.setItems(data);
    }

    public void remove(T t){
        tableViewObjects.remove(t);
        data = FXCollections.observableArrayList(tableViewObjects);
        table.setItems(data);
    }

    public TableView<T> getTable() {
        return table;
    }

    public void setTable(TableView<T> table) {
        this.table = table;
    }
}
