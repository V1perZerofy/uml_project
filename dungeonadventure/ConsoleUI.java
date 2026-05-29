package dungeonadventure;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class ConsoleUI {

    private static final int WIDTH = 64;
    private static final int LOG_LINES = 7;
    private static final int HP_SEGMENTS = 16;

    private static final char ESC = '';
    private static final String RESET = ESC + "[0m";
    private static final String BOLD = ESC + "[1m";
    private static final String RED = ESC + "[31m";
    private static final String GREEN = ESC + "[32m";
    private static final String YELLOW = ESC + "[33m";
    private static final String BLUE = ESC + "[34m";
    private static final String CYAN = ESC + "[36m";
    private static final String GRAY = ESC + "[90m";

    private static final String H = "─";
    private static final String V = "│";
    private static final String TL = "┌";
    private static final String TR = "┐";
    private static final String BL = "└";
    private static final String BR = "┘";
    private static final String FILL = "█";
    private static final String EMPTY = "░";
    private static final String MARK = "▶";
    private static final String DOT = "·";

    private final Deque<String> log = new ArrayDeque<>();
    private final PrintStream out;

    public ConsoleUI() {
        PrintStream stream;
        try {
            stream = new PrintStream(System.out, true, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            stream = System.out;
        }
        this.out = stream;
    }

    public void message(String text) {
        if (text == null) {
            return;
        }
        for (String entry : text.split("\n", -1)) {
            log.addLast(entry);
            while (log.size() > LOG_LINES) {
                log.removeFirst();
            }
        }
    }

    public void render(Room room, Player player) {
        StringBuilder sb = new StringBuilder();
        sb.append(ESC).append("[H").append(ESC).append("[2J");
        appendRoom(sb, room);
        appendLog(sb);
        appendStatus(sb, player);
        appendHint(sb);
        out.print(sb);
        out.flush();
    }

    public void prompt() {
        out.print(" " + CYAN + "> " + RESET);
        out.flush();
    }

    private void appendRoom(StringBuilder sb, Room room) {
        line(sb, topBorder("Dungeon Adventure"));
        line(sb, row(room.getDescription()));
        line(sb, row(""));
        if (room.hasMonster()) {
            Monster m = room.getMonster();
            line(sb, row("  " + YELLOW + MARK + RESET + " A " + RED + m.getName() + RESET
                    + " blocks your path. " + GRAY + "(" + m.getHealth() + " hp)" + RESET));
        }
        if (room.hasMerchant()) {
            line(sb, row("  " + YELLOW + MARK + RESET + " A merchant, " + CYAN
                    + room.getMerchant().getName() + RESET + ", waits here."));
        }
        if (room.hasItem()) {
            line(sb, row("  " + YELLOW + MARK + RESET + " You see a " + GREEN
                    + room.getItem().getName() + RESET + "."));
        }
        line(sb, row(""));
        String exits = String.join(", ", room.getExitDirections());
        line(sb, row(GRAY + "Exits: " + RESET + BLUE + (exits.isEmpty() ? "none" : exits) + RESET));
        line(sb, bottomBorder());
    }

    private void appendLog(StringBuilder sb) {
        line(sb, topBorder("Log"));
        List<String> entries = new ArrayList<>(log);
        for (int i = 0; i < LOG_LINES; i++) {
            String text = i < entries.size() ? entries.get(i) : "";
            line(sb, row(text));
        }
        line(sb, bottomBorder());
    }

    private void appendStatus(StringBuilder sb, Player player) {
        int hp = player.getHealth();
        int max = player.getMaxHealth();
        String status = " " + BOLD + "HP" + RESET + " " + hpBar(hp, max) + " "
                + hpColor(hp, max) + hp + "/" + max + RESET
                + "    " + YELLOW + "Gold " + player.getGold() + RESET
                + "    Items " + player.getInventory().size();
        line(sb, status);
    }

    private void appendHint(StringBuilder sb) {
        String sep = " " + GRAY + DOT + " ";
        line(sb, " " + GRAY + "go <dir>" + sep + "fight" + sep + "take" + sep + "talk" + sep
                + "buy <item>" + sep + "use <item>" + sep + "inv" + sep + "look" + sep + "quit" + RESET);
    }

    private String hpBar(int hp, int max) {
        int filled = max <= 0 ? 0 : (int) Math.round((double) hp / max * HP_SEGMENTS);
        if (filled < 0) {
            filled = 0;
        }
        if (filled > HP_SEGMENTS) {
            filled = HP_SEGMENTS;
        }
        return hpColor(hp, max) + repeat(FILL, filled) + RESET + GRAY + repeat(EMPTY, HP_SEGMENTS - filled) + RESET;
    }

    private String hpColor(int hp, int max) {
        double ratio = max <= 0 ? 0 : (double) hp / max;
        if (ratio >= 0.6) {
            return GREEN;
        }
        if (ratio >= 0.3) {
            return YELLOW;
        }
        return RED;
    }

    private String topBorder(String title) {
        StringBuilder b = new StringBuilder();
        b.append(TL).append(H);
        int used = 2;
        if (title != null && !title.isEmpty()) {
            b.append(" ").append(BOLD).append(CYAN).append(title).append(RESET).append(" ");
            used += title.length() + 2;
        }
        b.append(repeat(H, WIDTH - used - 1)).append(TR);
        return b.toString();
    }

    private String bottomBorder() {
        return BL + repeat(H, WIDTH - 2) + BR;
    }

    private String row(String content) {
        return V + pad(" " + content, WIDTH - 2) + V;
    }

    private void line(StringBuilder sb, String text) {
        sb.append(text).append("\n");
    }

    private String pad(String content, int width) {
        int vis = visibleLength(content);
        if (vis >= width) {
            return clip(content, width);
        }
        return content + repeat(" ", width - vis);
    }

    private String clip(String s, int max) {
        StringBuilder result = new StringBuilder();
        int vis = 0;
        int i = 0;
        while (i < s.length() && vis < max) {
            char c = s.charAt(i);
            if (c == ESC) {
                int j = s.indexOf('m', i);
                if (j >= 0) {
                    result.append(s, i, j + 1);
                    i = j + 1;
                    continue;
                }
            }
            result.append(c);
            vis++;
            i++;
        }
        result.append(RESET);
        return result.toString();
    }

    private int visibleLength(String s) {
        int vis = 0;
        int i = 0;
        while (i < s.length()) {
            char c = s.charAt(i);
            if (c == ESC) {
                int j = s.indexOf('m', i);
                if (j >= 0) {
                    i = j + 1;
                    continue;
                }
            }
            vis++;
            i++;
        }
        return vis;
    }

    private String repeat(String unit, int n) {
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < n; i++) {
            b.append(unit);
        }
        return b.toString();
    }
}