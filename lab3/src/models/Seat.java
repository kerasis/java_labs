package models;

public class Seat {
    private int row;
    private int number;
    private boolean isOccupied;

    public Seat(int row, int number) {
        this.row = row;
        this.number = number;
        this.isOccupied = false;
    }

    public int getRow() {
        return row;
    }

    public int getNumber() {
        return number;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    @Override
    public String toString() {
        return String.format("Ряд: %d, Место: %d, %s", 
            row, number, isOccupied ? "Занято" : "Свободно");
    }
} 