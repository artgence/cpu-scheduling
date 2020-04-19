package main;

//相关包引入
import java.net.URL;
import java.util.*;

import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

//控制器
public class Controller implements Initializable {

    // 与FXML控件对应
    @FXML
    private Button ADD;
    @FXML
    private Button BEGIN;
    @FXML
    private Button PAUSE;
    @FXML
    private Button SUSPEND;
    @FXML
    private Button RESUME;
    @FXML
    private Button EXIT;
    @FXML
    private TextField NAME;
    @FXML
    private TextField TIME;
    @FXML
    private TextField PRIORITY;
    @FXML
    private TextField SIZE;
    @FXML
    private TableView<Pcb> RUNNING;
    @FXML
    private TableColumn<Pcb, String> runningName;
    @FXML
    private TableColumn<Pcb, String> runningTime;
    @FXML
    private TableColumn<Pcb, String> runningPriority;
    @FXML
    private TableColumn<Pcb, String> runningSize;
    @FXML
    private TableColumn<Pcb, CheckBox> runningChecklist;
    @FXML
    private TableView<Pcb> WAITING;
    @FXML
    private TableColumn<Pcb, String> waitingName;
    @FXML
    private TableColumn<Pcb, String> waitingTime;
    @FXML
    private TableColumn<Pcb, String> waitingPriority;
    @FXML
    private TableColumn<Pcb, String> waitingSize;
    @FXML
    private TableColumn<Pcb, CheckBox> checklist;
    @FXML
    private TableView<Pcb> READY;
    @FXML
    private TableColumn<Pcb, String> readyName;
    @FXML
    private TableColumn<Pcb, String> readyTime;
    @FXML
    private TableColumn<Pcb, String> readyPriority;
    @FXML
    private TableColumn<Pcb, String> readySize;
    @FXML
    private TableColumn<Pcb, CheckBox> readyChecklist;
    @FXML
    private TableView<Pcb> BLOCKED;
    @FXML
    private TableColumn<Pcb, String> blockedName;
    @FXML
    private TableColumn<Pcb, String> blockedTime;
    @FXML
    private TableColumn<Pcb, String> blockedPriority;
    @FXML
    private TableColumn<Pcb, String> blockedSize;
    @FXML
    private TableColumn<Pcb, CheckBox> blockChecklist;
    @FXML
    private TableView<Pcb> TERMINATED;
    @FXML
    private TableColumn<Pcb, String> terminatedName;
    @FXML
    private TableColumn<Pcb, String> terminatedTime;
    @FXML
    private TableColumn<Pcb, String> terminatedPriority;
    @FXML
    private TableColumn<Pcb, String> terminatedSize;
    @FXML
    private TableView<Info> FREE;
    @FXML
    private TableColumn<Info, String> freeBase;
    @FXML
    private TableColumn<Info, String> freeLength;
    @FXML
    private TableView<Info> OCCUPIED;
    @FXML
    private TableColumn<Info, String> occupiedName;
    @FXML
    private TableColumn<Info, String> occupiedBase;
    @FXML
    private TableColumn<Info, String> occupiedLength;

    // 内存空间
    private boolean[] memory = new boolean[1024];

    // TableView操作需要的List
    private ObservableList<Pcb> waiting_queue = FXCollections.observableArrayList();
    private ObservableList<Pcb> suspend_queue = FXCollections.observableArrayList();
    private ObservableList<Pcb> ready_queue = FXCollections.observableArrayList();
    private ObservableList<Pcb> running_queue = FXCollections.observableArrayList();
    private ObservableList<Pcb> finish_queue = FXCollections.observableArrayList();
    private ObservableList<Info> free_queue = FXCollections.observableArrayList();
    private ObservableList<Info> occupied_queue = FXCollections.observableArrayList();

    // 停止标记
    private boolean stop = false;

    // 自己写一个checkbox类
    public static class checkbox {
        CheckBox checkbox = new CheckBox();

        ObservableValue<CheckBox> getCheckBox() {
            return new ObservableValue<CheckBox>() {
                @Override
                public void addListener(ChangeListener<? super CheckBox> listener) {

                }

                @Override
                public void removeListener(ChangeListener<? super CheckBox> listener) {

                }

                @Override
                public CheckBox getValue() {
                    return checkbox;
                }

                @Override
                public void addListener(InvalidationListener listener) {

                }

                @Override
                public void removeListener(InvalidationListener listener) {

                }
            };
        }

        Boolean isSelected() {
            return checkbox.isSelected();
        }

        void undo() {
            checkbox.setSelected(false);
        }

        public void choose() {
            checkbox.setSelected(true);
        }

    }

    // 添加按键
    public void Addition(ActionEvent event) {
        String name = NAME.getText().trim();
        String time = TIME.getText().trim();
        String size = SIZE.getText().trim();
        String priority = PRIORITY.getText().trim();
        if (!name.equals("") && !time.equals("") && !priority.equals("") && !size.equals("")) {
            waitingName.setCellValueFactory(cellData -> cellData.getValue().getName());
            waitingTime.setCellValueFactory(cellData -> cellData.getValue().getTime());
            waitingPriority.setCellValueFactory(cellData -> cellData.getValue().getPriority());
            waitingSize.setCellValueFactory(cellData -> cellData.getValue().getSize());
            Pcb program = new Pcb(name, time, priority, size);
            waiting_queue.add(program);
            WAITING.setItems(waiting_queue);
        } else {
            Alert warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("提示");
            warning.setHeaderText("有输入为空");
            Button warn = new Button();
            warn.setOnAction((ActionEvent e) -> {
                warning.showAndWait();
            });
            warning.show();
        }
        NAME.clear();
        TIME.clear();
        PRIORITY.clear();
        SIZE.clear();
    }

    // 退出按键
    public void exit(ActionEvent event) {
        NAME.setText("EXIT");
        EXIT.getScene().getWindow().hide();
    }

    // 停止按键
    public void pause(ActionEvent event) {
        stop = !stop;
    }

    // 初始化
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // TODO (don't really need to do anything here).

    }

    // 开始按键
    public void begin(ActionEvent event) {
        ObservableList<Pcb> runRemove = FXCollections.observableArrayList();
        ObservableList<Pcb> readyRemove = FXCollections.observableArrayList();
        int allocateCount = 0;
        int getCount = 0;
        boolean first = true;
        boolean[] flag = new boolean[50];

        Collections.sort(waiting_queue);

        if (!waiting_queue.isEmpty() && running_queue.size() != 1) {

            //遍历全部，能进则进
            for(Pcb o: waiting_queue) {
                if(search_and_locate(o)) {
                    flag[allocateCount] = true;
                }
                allocateCount++;
            }

            for(Pcb o: waiting_queue) {
                if(flag[getCount]) {
                    if(first) {
                        running_queue.add(o);
                        o.setStatus();
                        runRemove.add(o);
                        first = false;
                    }else {
                        ready_queue.add(o);
                        o.setStatus();
                        readyRemove.add(o);
                    }
                }
                getCount++;
            }
            waiting_queue.removeAll(runRemove);
            waiting_queue.removeAll(readyRemove);

            runningName.setCellValueFactory(cellData -> cellData.getValue().getName());
            runningTime.setCellValueFactory(cellData -> cellData.getValue().getTime());
            runningPriority.setCellValueFactory(cellData -> cellData.getValue().getPriority());
            runningSize.setCellValueFactory(cellData -> cellData.getValue().getSize());
            runningChecklist.setCellValueFactory(cellData -> cellData.getValue().cb.getCheckBox());
            RUNNING.setItems(running_queue);

            readyName.setCellValueFactory(cellData -> cellData.getValue().getName());
            readyTime.setCellValueFactory(cellData -> cellData.getValue().getTime());
            readyPriority.setCellValueFactory(cellData -> cellData.getValue().getPriority());
            readySize.setCellValueFactory(cellData -> cellData.getValue().getSize());
            readyChecklist.setCellValueFactory(cellData -> cellData.getValue().cb.getCheckBox());
            READY.setItems(ready_queue);
            //set the unuse memory
            free_list();
            //set the time slice 500ms
            Timer timer = new Timer();
            timer.schedule(new Task(timer), 1000, 500);
        } else {
            if (waiting_queue.isEmpty()) {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("提示");
                warning.setHeaderText("请添加进程");
                Button warn = new Button();
                warn.setOnAction((ActionEvent e) -> {
                    warning.showAndWait();
                });
                warning.show();
            } else {
                Alert warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("提示");
                warning.setHeaderText("已开始");
                Button warn = new Button();
                warn.setOnAction((ActionEvent e) -> {
                    warning.showAndWait();
                });
                warning.show();
            }
        }
    }

    // 挂起按键
    public void suspend(ActionEvent event) {
        ObservableList<Pcb> readyRemove = FXCollections.observableArrayList();
        ObservableList<Pcb> runningRemove2 = FXCollections.observableArrayList();
        ObservableList<Pcb> rrRemove = FXCollections.observableArrayList();

        //traversal the each Queue and remove the checked one
        for (Pcb o : ready_queue) {
            if (o.cb.isSelected()) {
                o.cb.undo();
                suspend_queue.add(o);
                readyRemove.add(o);
            }
        }


        for (Pcb o : running_queue) {
            if (o.cb.isSelected()) {
                o.cb.undo();
                suspend_queue.add(o);
                runningRemove2.add(o);
            }
        }

        ready_queue.removeAll(readyRemove);
        running_queue.removeAll(runningRemove2);

        blockedName.setCellValueFactory(cellData -> cellData.getValue().getName());
        blockedTime.setCellValueFactory(cellData -> cellData.getValue().getTime());
        blockedPriority.setCellValueFactory(cellData -> cellData.getValue().getPriority());
        blockedSize.setCellValueFactory(cellData -> cellData.getValue().getSize());
        blockChecklist.setCellValueFactory(cellData -> cellData.getValue().cb.getCheckBox());
        BLOCKED.setItems(suspend_queue);

        //when we suspend the progress is running
        if (running_queue.isEmpty() && !ready_queue.isEmpty()) {
            ready_queue.get(0).cb.choose();
            for (Pcb o : ready_queue) {
                if (o.cb.isSelected()) {
                    o.cb.undo();
                    running_queue.add(o);
                    rrRemove.add(o);
                }
            }
            ready_queue.removeAll(rrRemove);
        }

        Collections.sort(suspend_queue);

    }

    // 解挂按键
    public void resume(ActionEvent event) {
        ObservableList<Pcb> remove = FXCollections.observableArrayList();

        //remove from the suspend queue to resume
        for (Pcb o : suspend_queue) {
            if (o.cb.isSelected()) {
                o.cb.undo();
                ready_queue.add(o);
                remove.add(o);
            }
        }
        suspend_queue.removeAll(remove);
        readyName.setCellValueFactory(cellData -> cellData.getValue().getName());
        readyTime.setCellValueFactory(cellData -> cellData.getValue().getTime());
        readyPriority.setCellValueFactory(cellData -> cellData.getValue().getPriority());
        readySize.setCellValueFactory(cellData -> cellData.getValue().getSize());
        readyChecklist.setCellValueFactory(cellData -> cellData.getValue().cb.getCheckBox());
        READY.setItems(ready_queue);
        Collections.sort(ready_queue);
    }

    public static class Info {
        private final SimpleStringProperty base;
        private final SimpleStringProperty limit;
        private final SimpleStringProperty name;

        public Info(String name, int base, int limit) {
            this.name = new SimpleStringProperty(name);
            this.base = new SimpleStringProperty(base + "");
            this.limit = new SimpleStringProperty(limit + "");
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Info)) return false;
            Info info = (Info) o;
            return info.getIntBase() == this.getIntBase() &&
                    info.getIntLimit() == this.getIntLimit() &&
                    info.getStringName().equals(this.getStringName());
        }

        @Override
        public int hashCode() {
            return Objects.hash(base, limit, name);
        }

        SimpleStringProperty getbase() {
            return base;
        }

        SimpleStringProperty getlimit() {
            return limit;
        }

        SimpleStringProperty getname() {
            return name;
        }

        int getIntBase() {
            return Integer.parseInt(base.get());
        }

        int getIntLimit() {
            return Integer.parseInt(limit.get());
        }

        String getStringName() {
            return name.get();
        }
    }

    // Pcb类
    public static class Pcb implements Comparable<Pcb> {
        private final SimpleStringProperty name;
        private final SimpleStringProperty time;
        private final SimpleStringProperty priority;
        private final SimpleStringProperty size;
        private final checkbox cb;
        private boolean status;
        private int base;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Pcb)) return false;
            Pcb pcb = (Pcb) o;
            return pcb.getStringName().equals(this.getStringName()) &&
                    pcb.getIntTime() == this.getIntTime() &&
                    pcb.getIntPriority() == this.getIntPriority() &&
                    pcb.getIntSize() == this.getIntSize();
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, time, priority, size);
        }

        public Pcb(String name, String time, String priority, String size) {
            this.name = new SimpleStringProperty(name);
            this.time = new SimpleStringProperty(time);
            this.priority = new SimpleStringProperty(priority);
            this.size = new SimpleStringProperty(size);
            this.cb = new checkbox();
        }

        SimpleStringProperty getPriority() {
            return priority;
        }

        SimpleStringProperty getTime() {
            return time;
        }

        SimpleStringProperty getSize() {
            return size;
        }

        SimpleStringProperty getName() {
            return name;
        }

        void setTime(String time) {
            this.time.set(time);
        }

        void setPriority(String priority) {
            this.priority.set(priority);
        }

        String getStringName() {
            return name.getName();
        }

        int getIntTime() {
            return Integer.parseInt(time.get());
        }

        int getIntPriority() {
            return Integer.parseInt(priority.get());
        }

        int getIntSize() {
            return Integer.parseInt(size.get());
        }

        void setStatus() {
            this.status = !this.status;
        }

        void setBase(int base) {
            this.base = base;
        }

        int getBase() {
            return base;
        }

        public checkbox getCb() {
            return cb;
        }

        @Override
        public int compareTo(Pcb o) {
            int i = o.getIntPriority() - this.getIntPriority();
            if (i == 0) {
                int j = o.getIntTime() - this.getIntTime();
                if (j == 0)
                    return o.getStringName().compareTo(this.getStringName());
                return -j;
            }
            return i;
        }
    }

    // 分配内存
    private boolean search_and_locate(Pcb ob) {
        int n = 0;
        int count = 0;
        int begin = n;
        boolean found = false;
        //all progress size must less than 1024
        while (n < 1024) {
            // check unuse memory and measure its length
            if (!memory[n] && count < ob.getIntSize()) {
                count++;
            } else {
                //find and locate in a momory
                if (count == ob.getIntSize()) {
                    occupiedName.setCellValueFactory(cellData -> cellData.getValue().getname());
                    occupiedBase.setCellValueFactory(cellData -> cellData.getValue().getbase());
                    occupiedLength.setCellValueFactory(cellData -> cellData.getValue().getlimit());
                    Info cache = new Info(ob.getName().get(), begin, ob.getIntSize());
                    ob.setBase(begin);
                    occupied_queue.add(cache);
                    OCCUPIED.setItems(occupied_queue);
                    for (int i = begin; i <= begin + ob.getIntSize(); i++) {
                        memory[i] = true;
                    }
                    found = true;
                    break;
                }
                // find a new space to allocate
                else {
                    count = 0;
                    begin = n;
                }
            }
            n++;
        }
        return found;
    }

    //内存回收
    private void retrieve(Pcb ob) {
        ObservableList<Info> remove = FXCollections.observableArrayList();

        for (int i = ob.getBase(); i <= ob.getBase() + ob.getIntSize(); i++) {
            memory[i] = false;
        }

        for (Info o : occupied_queue) {
            if (o.getname().get().equals(ob.getName().get())) {
                remove.add(o);
            }
        }
        occupied_queue.removeAll(remove);
    }

    //空闲列表
    private void free_list() {
        ObservableList<Info> remove = FXCollections.observableArrayList();
        int limit = 0;
        int n = 0;
        int base = n;

        remove.addAll(free_queue);
        free_queue.removeAll(remove);

        while (n <= 1024) {
            // find the length of memory to release
            if (n < 1024 && !memory[n]) {
                limit++;
            } else {
                if(limit > 0) {
                    freeBase.setCellValueFactory(cellData -> cellData.getValue().getbase());
                    freeLength.setCellValueFactory(cellData -> cellData.getValue().getlimit());
                    Info cache = new Info("Null" + n, base, limit);
                    free_queue.add(cache);
                    FREE.setItems(free_queue);
                    base = n + 1;
                    limit = 0;
                }
                // still not find a start memory
                else
                    base++;
            }
            n++;
        }
    }

    // Timer执行类
    class Task extends TimerTask {
        private Timer timer;

        Task(Timer timer) {
            this.timer = timer;
        }

        @Override
        public void run() {
            ObservableList<Pcb> runRemove = FXCollections.observableArrayList();
            ObservableList<Pcb> readyRemove = FXCollections.observableArrayList();
            ObservableList<Pcb> waitRemove = FXCollections.observableArrayList();
            ObservableList<Pcb> change = FXCollections.observableArrayList();
            int count1 = 0;
            int count2 = 0;
            boolean[] flag = new boolean[50];

            //Pause the process
            while (stop) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            //All finished
            if (running_queue.isEmpty() && suspend_queue.isEmpty() && ready_queue.isEmpty()) {
                this.timer.cancel();
                System.out.println("#### 程序结束 ####");
            }

            //running队列非空
            if (!running_queue.isEmpty()) {

                //高级优先权调度
                if (!ready_queue.isEmpty() && running_queue.get(0).getIntPriority() < ready_queue.get(0).getIntPriority()) {
                    Collections.sort(ready_queue);
                    change.add(running_queue.get(0));
                    running_queue.removeAll(change);
                    running_queue.add(ready_queue.get(0));
                    ready_queue.remove(0);
                    ready_queue.add(change.get(0));
                }

                //优先权变化
                running_queue.get(0).setTime(running_queue.get(0).getIntTime() - 1 + "");
                running_queue.get(0).setPriority(running_queue.get(0).getIntPriority() - 3 + "");

                //ready queue increase priority
                if (!ready_queue.isEmpty()) {
                    for (Pcb o : ready_queue) {
                        o.setPriority(o.getIntPriority() + 1 + "");
                    }
                }
                Collections.sort(ready_queue);

                //进程结束
                if (running_queue.get(0).getIntTime() == 0) {
                    finish_queue.add(running_queue.get(0));
                    retrieve(running_queue.get(0));
                    running_queue.get(0).setStatus();
                    runRemove.add(running_queue.get(0));
                    free_list();
                }
                running_queue.removeAll(runRemove);
                terminatedName.setCellValueFactory(cellData -> cellData.getValue().getName());
                terminatedTime.setCellValueFactory(cellData -> cellData.getValue().getTime());
                terminatedPriority.setCellValueFactory(cellData -> cellData.getValue().getPriority());
                terminatedSize.setCellValueFactory(cellData -> cellData.getValue().getSize());
                TERMINATED.setItems(finish_queue);

                //添加到running队列中
                if (running_queue.isEmpty() && !ready_queue.isEmpty()) {
                    running_queue.add(ready_queue.get(0));
                    readyRemove.add(ready_queue.get(0));
                }
                ready_queue.removeAll(readyRemove);

                //a progress add in the wait queue when the cpu is running
                //按内存限制来进入内存
                if(!waiting_queue.isEmpty()) {
                    //遍历全部，能进则进
                    for(Pcb o: waiting_queue) {
                        if(search_and_locate(o)) {
                            flag[count1] = true;
                        }
                        count1++;
                    }
                    for(Pcb o: waiting_queue) {
                        if(flag[count2]) {
                            ready_queue.add(o);
                            o.setStatus();
                            waitRemove.add(o);
                        }
                        count2++;
                    }
                    waiting_queue.removeAll(waitRemove);
                    free_list();
                }

            }

            //挂起队列存在进程并且running和ready队列为空
            if (running_queue.isEmpty() && (!suspend_queue.isEmpty() || !ready_queue.isEmpty())) {
                if (!ready_queue.isEmpty()) {

                    Collections.sort(ready_queue);
                    running_queue.add(ready_queue.get(0));
                    ready_queue.removeAll(ready_queue.get(0));

                    if (!running_queue.isEmpty()) {
                        if (!ready_queue.isEmpty()
                                && running_queue.get(0).getIntPriority() < ready_queue.get(0).getIntPriority()) {
                            change.add(running_queue.get(0));
                            running_queue.removeAll(change);
                            running_queue.add(ready_queue.get(0));
                            ready_queue.remove(0);
                            ready_queue.add(change.get(0));
                            Collections.sort(ready_queue);
                        }

                        running_queue.get(0).setTime(running_queue.get(0).getIntTime() - 1 + "");
                        running_queue.get(0).setPriority(running_queue.get(0).getIntPriority() - 3 + "");

                        if (!ready_queue.isEmpty()) {
                            for (Pcb o : ready_queue) {
                                o.setPriority(o.getIntPriority() + 1 + "");
                            }
                        }

                        if (running_queue.get(0).getIntTime() == 0) {
                            finish_queue.add(running_queue.get(0));
                            runRemove.add(running_queue.get(0));
                        }
                        running_queue.removeAll(runRemove);
                        terminatedName.setCellValueFactory(cellData -> cellData.getValue().getName());
                        terminatedTime.setCellValueFactory(cellData -> cellData.getValue().getTime());
                        terminatedPriority.setCellValueFactory(cellData -> cellData.getValue().getPriority());
                        terminatedSize.setCellValueFactory(cellData -> cellData.getValue().getSize());
                        TERMINATED.setItems(finish_queue);

                        if (running_queue.isEmpty() && !ready_queue.isEmpty()) {
                            running_queue.add(ready_queue.get(0));
                            readyRemove.add(ready_queue.get(0));
                        }
                        ready_queue.removeAll(readyRemove);

                        //按内存限制来进入内存
                        if(!waiting_queue.isEmpty()) {
                            //遍历全部，能进则进
                            for(Pcb o: waiting_queue) {
                                if(search_and_locate(o)) {
                                    flag[count1] = true;
                                }
                                count1++;
                            }

                            for(Pcb o: waiting_queue) {
                                if(flag[count2]) {
                                    ready_queue.add(o);
                                    o.setStatus();
                                    waitRemove.add(o);
                                }
                                count2++;
                            }
                            waiting_queue.removeAll(waitRemove);
                            free_list();
                        }
                    }
                } else {
                    //simulate 1 sec
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

        }
    }
}
