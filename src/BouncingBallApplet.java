import javax.swing.*;
import java.awt.*;

// Основной класс, который наследуется от JPanel для отрисовки и анимации шарика
public class BouncingBallApplet extends JPanel {
    // Начальные параметры для шарика
    private int ballRadius = 30; // Радиус шарика
    private int ballX = ballRadius + 50; // Начальная координата X
    private int ballY = ballRadius + 20; // Начальная координата Y
    private int ballSpeedX = 10;   // Скорость шарика по X
    private int ballSpeedY = 10;   // Скорость шарика по Y
    private static final int UPDATE_RATE = 30; // Частота обновления в мс

    // Параметры притяжения к стенкам
    private static final int GRAVITY = 0;

    // Конструктор класса
    public BouncingBallApplet() {
        // Запуск анимации в отдельном потоке
        new Thread(() -> {
            while (true) {
                moveBall(); // Обновление положения шарика
                repaint(); // Перерисовка панели

                // Пауза между обновлениями для контроля скорости анимации
                try {
                    Thread.sleep(1000 / UPDATE_RATE);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    // Метод для обновления положения шарика и его скорости
    private void moveBall() {
        // Обновление положения шарика
        ballX += ballSpeedX;
        ballY += ballSpeedY;

        // Проверка столкновения шарика со стенками и изменение направления, если столкновение произошло
        if (ballX - ballRadius < 0) {
            ballSpeedX = -ballSpeedX; // Отражение от стенки
            ballX = ballRadius; // Коррекция положения
        } else if (ballX + ballRadius > getWidth()) {
            ballSpeedX = -ballSpeedX;
            ballX = getWidth() - ballRadius;
        }
        if (ballY - ballRadius < 0) {
            ballSpeedY = -ballSpeedY;
            ballY = ballRadius;
        } else if (ballY + ballRadius > getHeight()) {
            ballSpeedY = -ballSpeedY;
            ballY = getHeight() - ballRadius;
        }

        // Применение "силы притяжения" к шарику
        if (ballX - ballRadius < 50) ballSpeedX += GRAVITY;
        if (ballX + ballRadius > getWidth() - 50) ballSpeedX -= GRAVITY;
        if (ballY - ballRadius < 50) ballSpeedY += GRAVITY;
        if (ballY + ballRadius > getHeight() - 50) ballSpeedY -= GRAVITY;
    }

    // Метод для отрисовки шарика и рамки
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Очистка панели перед отрисовкой

        // Отрисовка шарика
        g.setColor(Color.BLUE); // Установка цвета для шарика
        g.fillOval(ballX - ballRadius, ballY - ballRadius, ballRadius * 2, ballRadius * 2); // Рисование шарика

        // Отрисовка рамки
        g.setColor(Color.BLACK); // Установка цвета для рамки
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1); // Рисование рамки
    }

    // Главный метод для запуска программы
    public static void main(String[] args) {
        JFrame frame = new JFrame("Bouncing Ball Applet"); // Создание окна
        BouncingBallApplet applet = new BouncingBallApplet(); // Создание панели с анимацией
        frame.add(applet); // Добавление панели в окно
        frame.setSize(400, 400); // Установка размера окна
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Установка операции по умолчанию при закрытии окна
        frame.setVisible(true); // Делаем окно видимым
    }
}
