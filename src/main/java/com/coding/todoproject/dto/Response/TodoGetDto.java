package com.coding.todoproject.dto.Response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class TodoGetDto {
    private String id;
    private String title;
    private LocalDate date;
    private LocalDateTime createdAt;
    private boolean marked;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isMarked() {
        return marked;
    }

    public void setMarked(boolean marked) {
        this.marked = marked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodoGetDto that = (TodoGetDto) o;
        return marked == that.marked && Objects.equals(id, that.id) &&
                Objects.equals(title, that.title) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, date, marked);
    }
}
