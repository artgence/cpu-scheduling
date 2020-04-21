import main.Controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import main.Main;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit.ApplicationTest;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.loadui.testfx.GuiTest.find;


public class MainTest extends ApplicationTest {

    @Override
    public void start(Stage stage) throws Exception {
        Parent mainNode = FXMLLoader.load(getClass().getResource("resources/cpu-scheduling.fxml"));
        Scene scene = new Scene(mainNode, 1254, 758);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("resources/application.css")).toExternalForm());
        stage.setScene(scene);
        stage.setTitle("CPU Scheduling");
        stage.show();
        stage.toFront();
    }

    @Before
    public void setUp() throws Exception {}

    @After
    public void tearDown() throws Exception {
        FxToolkit.hideStage();
        release(new KeyCode[]{});
        release(new MouseButton[]{});
    }

    @Test
    public void testAddButtonLabel() {
        final Button add = find("#ADD");
        Assert.assertNotNull(add);
        assertEquals(add.getText(), "添加");
    }

    @Test
    public void testBeginButtonLabel() {
        final Button begin = find("#BEGIN");
        Assert.assertNotNull(begin);
        assertEquals(begin.getText(), "开始");
    }

    @Test
    public void testPauseButtonLabel() {
        final Button pause = find("#PAUSE");
        Assert.assertNotNull(pause);
        assertEquals(pause.getText(), "暂停/继续");
    }

    @Test
    public void testSuspendButtonLabel() {
        final Button suspend = find("#SUSPEND");
        Assert.assertNotNull(suspend);
        assertEquals(suspend.getText(), "挂起");
    }

    @Test
    public void testResumeButtonLabel() {
        final Button resume = find("#RESUME");
        Assert.assertNotNull(resume);
        assertEquals(resume.getText(), "解挂");
    }

    @Test
    public void testExitButtonLabel() {
        final Button exit = find("#EXIT");
        Assert.assertNotNull(exit);
        assertEquals(exit.getText(), "退出");
    }

    @Test
    public void testNameTextField() {
        final TextField name = find("#NAME");
        clickOn("#NAME");
        write("IDEA");
        assertEquals(name.getText(), "IDEA");
    }

    @Test
    public void testTimeTextField() {
        final TextField time = find("#TIME");
        clickOn("#TIME");
        write("10");
        assertEquals(time.getText(), "10");
    }

    @Test
    public void testPriorityTextField() {
        final TextField priority = find("#PRIORITY");
        clickOn("#PRIORITY");
        write("20");
        assertEquals(priority.getText(), "20");
    }

    @Test
    public void testSizeTextField() {
        final TextField size = find("#SIZE");
        clickOn("#SIZE");
        write("300");
        assertEquals(size.getText(), "300");
    }

    @Test
    public void testAddButton() {
        final TextField name = find("#NAME");
        final TextField time = find("#TIME");
        final TextField priority = find("#PRIORITY");
        final TextField size = find("#SIZE");
        final TableView<Controller.Pcb> wait = find("#WAITING");
        clickOn("#NAME");
        write("IDEA");
        clickOn("#TIME");
        write("10");
        clickOn("#PRIORITY");
        write("200");
        clickOn("#SIZE");
        write("300");
        Controller.Pcb demo = new Controller.Pcb(name.getText(), time.getText(), priority.getText(), size.getText());
        clickOn("#ADD");
        assertTrue(wait.getItems().contains(demo));
        assertEquals(name.getText(), "");
        assertEquals(time.getText(), "");
        assertEquals(priority.getText(), "");
        assertEquals(size.getText(), "");
    }

    @Test
    public void testBeginButton() {
        final TextField name = find("#NAME");
        final TextField time = find("#TIME");
        final TextField priority = find("#PRIORITY");
        final TextField size = find("#SIZE");
        final TableView<Controller.Pcb> run = find("#RUNNING");
        final TableView<Controller.Pcb> wait = find("#WAITING");
        clickOn("#NAME");
        write("IDEA");
        clickOn("#TIME");
        write("10");
        clickOn("#PRIORITY");
        write("200");
        clickOn("#SIZE");
        write("300");
        Controller.Pcb demo = new Controller.Pcb(name.getText(), time.getText(), priority.getText(), size.getText());
        clickOn("#ADD");
        clickOn("#BEGIN");
        assertTrue(run.getItems().contains(demo));
        assertEquals(wait.getItems().size(), 0);
    }

    @Test
    public void testPauseButton() {
        final TextField name = find("#NAME");
        final TextField time = find("#TIME");
        final TextField priority = find("#PRIORITY");
        final TextField size = find("#SIZE");
        final TableView<Controller.Pcb> run = find("#RUNNING");
        clickOn("#NAME");
        write("IDEA");
        clickOn("#TIME");
        write("10");
        clickOn("#PRIORITY");
        write("200");
        clickOn("#SIZE");
        write("300");
        final Controller.Pcb demo = new Controller.Pcb(name.getText(), time.getText(), priority.getText(), size.getText());
        clickOn("#ADD");
        clickOn("#BEGIN");
        clickOn("#PAUSE");
        sleep(300);
        assertTrue(run.getItems().contains(demo));
    }

    @Test
    public void testSuspendButton() {
        final TextField name = find("#NAME");
        final TextField time = find("#TIME");
        final TextField priority = find("#PRIORITY");
        final TextField size = find("#SIZE");
        final TableView<Controller.Pcb> block = find("#BLOCKED");
        final TableView<Controller.Pcb> run = find("#RUNNING");
        clickOn("#NAME");
        write("IDEA");
        clickOn("#TIME");
        write("10");
        clickOn("#PRIORITY");
        write("200");
        clickOn("#SIZE");
        write("300");
        final Controller.Pcb demo = new Controller.Pcb(name.getText(), time.getText(), priority.getText(), size.getText());
        clickOn("#ADD");
        clickOn("#BEGIN");
        clickOn("#PAUSE");
        run.getItems().get(0).getCb().choose();
        clickOn("#SUSPEND");
        assertTrue(block.getItems().contains(demo));
        assertEquals(run.getItems().size(), 0);
    }

    @Test
    public void testResumeButton() {
        final TextField name = find("#NAME");
        final TextField time = find("#TIME");
        final TextField priority = find("#PRIORITY");
        final TextField size = find("#SIZE");
        final TableView<Controller.Pcb> block = find("#BLOCKED");
        final TableView<Controller.Pcb> run = find("#RUNNING");
        final TableView<Controller.Pcb> ready = find("#READY");
        clickOn("#NAME");
        write("IDEA");
        clickOn("#TIME");
        write("10");
        clickOn("#PRIORITY");
        write("200");
        clickOn("#SIZE");
        write("300");
        final Controller.Pcb demo = new Controller.Pcb(name.getText(), time.getText(), priority.getText(), size.getText());
        clickOn("#ADD");
        clickOn("#BEGIN");
        clickOn("#PAUSE");
        run.getItems().get(0).getCb().choose();
        clickOn("#SUSPEND");
        block.getItems().get(0).getCb().choose();
        clickOn("#RESUME");
        assertTrue(ready.getItems().contains(demo));
    }

    @Test
    public void testExitButton() {
        final TextField name = find("#NAME");
        clickOn("#EXIT");
        assertEquals(name.getText(), "EXIT");
    }

    @Test
    public void testFinalFreeTableView() {
        final TableView<Controller.Info> free = find("#FREE");
        clickOn("#NAME");
        write("IDEA");
        clickOn("#TIME");
        write("10");
        clickOn("#PRIORITY");
        write("200");
        clickOn("#SIZE");
        write("300");
        final Controller.Info demo = new Controller.Info("Null1024", 0, 1024);
        clickOn("#ADD");
        clickOn("#BEGIN");
        sleep(6000);
        assertTrue(free.getItems().contains(demo));
    }

    @Test
    public void testBeginWaitingTableView() {
        final TextField name = find("#NAME");
        final TextField time = find("#TIME");
        final TextField priority = find("#PRIORITY");
        final TextField size = find("#SIZE");
        final TableView<Controller.Pcb> wait = find("#WAITING");
        clickOn("#NAME");
        write("IDEA");
        clickOn("#TIME");
        write("10");
        clickOn("#PRIORITY");
        write("200");
        clickOn("#SIZE");
        write("300");
        final Controller.Pcb demo = new Controller.Pcb(name.getText(), time.getText(), priority.getText(), size.getText());
        clickOn("#ADD");
        assertTrue(wait.getItems().contains(demo));
    }

    @Test
    public void testBeginRunningTableView() {
        final TextField name = find("#NAME");
        final TextField time = find("#TIME");
        final TextField priority = find("#PRIORITY");
        final TextField size = find("#SIZE");
        final TableView<Controller.Pcb> run = find("#RUNNING");
        clickOn("#NAME");
        write("ECLIPSE");
        clickOn("#TIME");
        write("10");
        clickOn("#PRIORITY");
        write("100");
        clickOn("#SIZE");
        write("200");
        final Controller.Pcb demo = new Controller.Pcb(name.getText(), time.getText(), priority.getText(), size.getText());
        clickOn("#ADD");
        clickOn("#BEGIN");
        assertTrue(run.getItems().contains(demo));
    }

    @Test
    public void testBeginReadyTableView() {
        final TextField name = find("#NAME");
        final TextField time = find("#TIME");
        final TextField priority = find("#PRIORITY");
        final TextField size = find("#SIZE");
        final TableView<Controller.Pcb> ready = find("#READY");
        clickOn("#NAME");
        write("IDEA");
        clickOn("#TIME");
        write("10");
        clickOn("#PRIORITY");
        write("200");
        clickOn("#SIZE");
        write("300");
        clickOn("#ADD");

        clickOn("#NAME");
        write("ECLIPSE");
        clickOn("#TIME");
        write("5");
        clickOn("#PRIORITY");
        write("100");
        clickOn("#SIZE");
        write("200");
        final Controller.Pcb demo = new Controller.Pcb(name.getText(), time.getText(), priority.getText(), size.getText());
        clickOn("#ADD");

        clickOn("#BEGIN");
        assertTrue(ready.getItems().contains(demo));
    }

    @Test
    public void testBeginBlockedTableView() {
        final TextField name = find("#NAME");
        final TextField time = find("#TIME");
        final TextField priority = find("#PRIORITY");
        final TextField size = find("#SIZE");
        final TableView<Controller.Pcb> block = find("#BLOCKED");
        final TableView<Controller.Pcb> run = find("#RUNNING");
        clickOn("#NAME");
        write("ECLIPSE");
        clickOn("#TIME");
        write("5");
        clickOn("#PRIORITY");
        write("100");
        clickOn("#SIZE");
        write("200");
        final Controller.Pcb demo = new Controller.Pcb(name.getText(), time.getText(), priority.getText(), size.getText());
        clickOn("#ADD");
        clickOn("#BEGIN");
        run.getItems().get(0).getCb().choose();
        clickOn("#SUSPEND");
        assertTrue(block.getItems().contains(demo));
    }

    @Test
    public void test100SizeOccupiedTableView() {
        final TextField name = find("#NAME");
        final TextField size = find("#SIZE");
        final TableView<Controller.Info> occupied = find("#OCCUPIED");
        clickOn("#NAME");
        write("IDEA");
        clickOn("#TIME");
        write("10");
        clickOn("#PRIORITY");
        write("200");
        clickOn("#SIZE");
        write("300");
        final Controller.Info demo = new Controller.Info(name.getText(), 0, Integer.parseInt(size.getText()));
        clickOn("#ADD");
        clickOn("#BEGIN");
        assertTrue(occupied.getItems().contains(demo));
    }
}
