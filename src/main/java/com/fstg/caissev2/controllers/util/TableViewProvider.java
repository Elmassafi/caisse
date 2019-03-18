package com.fstg.caissev2.controllers.util;



import com.fstg.caissev2.Model.bean.Categorie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableViewProvider<T> {

    protected List<String> tableViewItems= new ArrayList();
    protected List<T> list = new ArrayList<T>();
    protected TableView<T> table;

    private ObservableList<T> data = FXCollections.observableArrayList(list);

    public TableViewProvider(TableView<T> table,List<String> attributesNames) {
        this.tableViewItems.addAll(attributesNames);
        this.table = table;
        fillTable();
    }
    public TableViewProvider(TableView<T> table,String... values) {
        this.tableViewItems.addAll(Arrays.asList(values));
        this.table = table;
        this.list = list;
        fillTable();
    }
    public TableViewProvider(TableView<T> table, List<T> list,String... values) {
        this.tableViewItems.addAll(Arrays.asList(values));
        this.table = table;
        this.list = list;
        fillTable();
    }

    public void select(T t){
        table.getSelectionModel().select(t);
    }

    private void fillTable(){
        for (int i = 0; i < tableViewItems.size(); i++) {
            TableColumn<T, ?> column = new TableColumn<>(tableViewItems.get(i));
            column.setCellValueFactory(new PropertyValueFactory<>(tableViewItems.get(i)));
            table.getColumns().add(column);
        }
        data = FXCollections.observableArrayList(list);
        table.setItems(data);
    }

    public List<T> getList() {
        return list;
    }

    public void addItem(T t){
        list.add(t);
        data = FXCollections.observableArrayList(list);
        table.setItems(data);
    }
    
    public void remove(T t){
        list.remove(t);
        data = FXCollections.observableArrayList(list);
        table.setItems(data);
    }

    public void setList(List<T> list){
        this.list=list;
        data = FXCollections.observableArrayList(list);
        table.setItems(data);
    }

    public TableView<T> getTable() {
        return table;
    }

    public void setTable(TableView<T> table) {
        this.table = table;
    }
}
