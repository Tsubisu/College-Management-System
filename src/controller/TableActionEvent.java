package controller;

public interface TableActionEvent {
    public void onUpdate(int row);
    public void onDelete(int row);
}
