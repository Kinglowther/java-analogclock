import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;

class Main extends JPanel {
    private int hour;
    private int minute;
    private int second;

    public Main() {
        Thread clockThread = new Thread(() -> {
            while (true) {
                updateClock();
                repaint();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        clockThread.start();
    }

    private void updateClock() {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
        String time = sdf.format(new Date());
        String[] timeParts = time.split(":");
        hour = Integer.parseInt(timeParts[0]);
        minute = Integer.parseInt(timeParts[1]);
        second = Integer.parseInt(timeParts[2]);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int width = getWidth();
        int height = getHeight();
        int size = Math.min(width, height) - 20;
        int centerX = width / 2;
        int centerY = height / 2;

        g.setColor(Color.WHITE);
        g.fillRect(0, 0, width, height);

        g.setColor(Color.BLACK);
        g.drawOval(centerX - size / 2, centerY - size / 2, size, size);

        // Draw hour hand
        int hourHandLength = size / 4;
        double hourAngle = Math.toRadians(90 - (hour % 12) * 30);
        int hourHandX = (int) (centerX + hourHandLength * Math.cos(hourAngle));
        int hourHandY = (int) (centerY - hourHandLength * Math.sin(hourAngle));
        g.drawLine(centerX, centerY, hourHandX, hourHandY);

        // Draw minute hand
        int minuteHandLength = size / 3;
        double minuteAngle = Math.toRadians(90 - minute * 6);
        int minuteHandX = (int) (centerX + minuteHandLength * Math.cos(minuteAngle));
        int minuteHandY = (int) (centerY - minuteHandLength * Math.sin(minuteAngle));
        g.drawLine(centerX, centerY, minuteHandX, minuteHandY);

        // Draw second hand
        int secondHandLength = size / 2;
        double secondAngle = Math.toRadians(90 - second * 6);
        int secondHandX = (int) (centerX + secondHandLength * Math.cos(secondAngle));
        int secondHandY = (int) (centerY - secondHandLength * Math.sin(secondAngle));
        g.setColor(Color.RED);
        g.drawLine(centerX, centerY, secondHandX, secondHandY);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Analog Clock");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        Main clock = new Main();
        frame.add(clock);

        frame.setVisible(true);
    }
}
