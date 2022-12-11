package com.coding.todoproject.dto.Request;

import java.time.LocalDate;
import java.util.Objects;

public class TodoUpdateDto {
    String id;
    String title;
    LocalDate date;
    boolean marked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoUpdateDto that = (TodoUpdateDto) o;
        return marked == that.marked && Objects.equals(id, that.id) && Objects.equals(title, that.title) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date, marked);
    }
}
