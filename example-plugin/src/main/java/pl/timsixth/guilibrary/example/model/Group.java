package pl.timsixth.guilibrary.example.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
@RequiredArgsConstructor
@Getter
public enum Group {

    ADMIN(ChatColor.RED),
    MODERATOR(ChatColor.GREEN);

    private final ChatColor color;
}
