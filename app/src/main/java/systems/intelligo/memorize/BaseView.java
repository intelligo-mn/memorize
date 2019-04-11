package systems.intelligo.memorize;

public interface BaseView<T> {

    void setPresenter(T presenter);

    void showToast(String message);
}