import java.util.ArrayDeque;
import java.util.Deque;

public class Calculator {
    private int currentValue = 0;
    private Deque<Command> history = new ArrayDeque<>();
    private Deque<Command> undone = new ArrayDeque<>();

    private abstract class CalculatorCommand implements Command {
        protected final int value;
        protected int snapshot;

        protected CalculatorCommand(int value) {
            this.value = value;
            this.snapshot = currentValue;
        }

        @Override
        public void undo() {
            currentValue = snapshot;
        }
    }

    /**
     * 加法
     */
    private class AddCommand extends CalculatorCommand {
        public AddCommand(int value) {
            super(value);
        }

        @Override
        public void execute() {
            snapshot = currentValue;
            currentValue += value;
            undone.clear();
        }
    }

    /**
     * 减法
     */
    private class MinusCommand extends CalculatorCommand {
        public MinusCommand(int value) {
            super(value);
        }

        @Override
        public void execute() {
            snapshot = currentValue;
            currentValue -= value;
            undone.clear();
        }
    }


    /**
     * 乘法
     */
    private class TimesCommand extends CalculatorCommand {
        public TimesCommand(int value) {
            super(value);
        }

        @Override
        public void execute() {
            snapshot = currentValue;
            currentValue *= value;
            undone.clear();
        }
    }

    /**
     * 除法
     */
    private class DividedByCommand extends CalculatorCommand {
        public DividedByCommand(int value) {
            super(value);
        }

        @Override
        public void execute() {
            snapshot = currentValue;
            currentValue /= value;
            undone.clear();
        }
    }

    public void add(int value) {
        Command cmd = new AddCommand(value);
        cmd.execute();
        history.push(cmd);
    }

    public void minus(int value) {
        Command cmd = new MinusCommand(value);
        cmd.execute();
        history.push(cmd);
    }

    public void times(int value) {
        Command cmd = new TimesCommand(value);
        cmd.execute();
        history.push(cmd);
    }

    public void dividedBy(int value) {
        Command cmd = new DividedByCommand(value);
        cmd.execute();
        history.push(cmd);
    }

    public void undo() {
        if (!history.isEmpty()) {
            Command cmd = history.pop();
            cmd.undo();
            undone.push(cmd);
        }
    }

    public void redo() {
        if (!undone.isEmpty()) {
            Command cmd = undone.pop();
            cmd.execute();
            history.push(cmd);
        }
    }

    public int getCurrentValue() {
        return currentValue;
    }
}