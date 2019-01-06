package org.geepawhill.contentment.utility;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import org.geepawhill.contentment.test.ContentmentTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ColorChangerTest extends ContentmentTest {
    @Test
    public void changesStrokeOnLine() {
        Line line = new Line(0d, 0d, 100d, 100d);
        line.setStroke(Color.RED);
        line.setFill(Color.RED);
        ColorChanger changer = new ColorChanger(Color.BLUE);
        changer.accept(line);
        assertThat(line.getStroke()).isEqualTo(Color.BLUE);
        assertThat(line.getFill()).isEqualTo(Color.RED);
        assertThat(changer.getResult()).isEqualTo(Color.RED);
    }

    @Test
    public void alsoChangesFillOnText() {
        Text text = new Text();
        text.setStroke(Color.RED);
        text.setFill(Color.RED);
        ColorChanger changer = new ColorChanger(Color.BLUE);
        changer.accept(text);
        assertThat(text.getStroke()).isEqualTo(Color.BLUE);
        assertThat(text.getFill()).isEqualTo(Color.BLUE);
        assertThat(changer.getResult()).isEqualTo(Color.RED);
    }

}
