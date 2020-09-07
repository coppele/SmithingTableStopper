package coppele.smithingtablestopper;


import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class SmithingTableStopper extends JavaPlugin implements Listener {

    FileConfiguration config = getConfig();

    @Override
    public void onEnable() {
        //プラグインが起動した場合"config.yml"をあれば読み込み、なければ作成します！
        this.saveDefaultConfig();
        config = getConfig();
        //そしてログを残します！
        getLogger().info("Enabling SmithingTableStopper v1.0.0");
        getServer().getPluginManager().registerEvents(this,this);
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling SmithingTableStopper v1.0.0");
    }

    @EventHandler
    public void onClick(InventoryOpenEvent e) {
        //クリックしたのが"InventoryType.SMITHING"の場合以下のプログラムを実行します！(∩´∀｀)∩
        if (e.getInventory().getType() == InventoryType.SMITHING) {
            //権限があればなにもしません！
            if (e.getPlayer().hasPermission("SmithingTableStopper.sts.op")) return;
            //コンフィグが存在しているか確認します！
            if (!config.contains("STSOperation")){
                //なければ警告を出します！
                getLogger().warning("config.ymlが存在しません！");
            //STSOperationに"true"か"false"かを伺います！
            } else if (config.getBoolean("STSOperation")) {
                //trueでしたら使用できないように強制終了。falseでしたらなにもしません。
                e.setCancelled(true);
                e.getPlayer().sendMessage("§7§l[§8§lST§c§lS§7§l]§4 申し訳ありませんが鍛冶台は使用できません…。");
            //trueにもfalseにも設定されていない場合警告を出します！
            } else if (!(config.getBoolean("STSOperation") || !config.getBoolean("STSOperation"))) {
                getLogger().warning("config.ymlの\"STSOperation\"がtureまたはfalseに設定されていません！");
            }
        }
    }
    /*
    思った以上にシンプルですね…(´･ω･`)
    たきさんの言う通りでした…
    */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("sts")) {
            if (args.length == 0) {
                //"/sts"の後に何もなければヘルプを表示します！
                sender.sendMessage("§7§l[§8§lST§c§lS§7§l]§8§l S§7mithing§8§lT§7able§c§lS§ctopper §fHelp");
                sender.sendMessage("§7§l[§8§lST§c§lS§7§l]§f /sts <true|false> -起動/停止します");
                sender.sendMessage("§7§l[§8§lST§c§lS§7§l]§f /sts <on|off> -起動/停止します");
                return true;
                //"args[0]"が"true"または"on"で既にtrueではない場合"STSOperation"を"true"にします！
            }else if (args[0].equals("true")||args[0].equals("on")) {
                if (config.getBoolean("STSOperation")) {
                    sender.sendMessage("§7§l[§8§lST§c§lS§7§l]§c すでに true に設定済みです！");
                    return true;
                }
                config.set("STSOperation",true);
                saveConfig();
                sender.sendMessage("§7§l[§8§lST§c§lS§7§l]§e config設定 STSOperation を true に変更しました！");
                return true;
                //"args[0]"が"false"または"off"で既にfalseではない場合"STSOperation"を"false"にします！
            }else if (args[0].equals("false")||args[0].equals("off")) {
                if (!config.getBoolean("STSOperation")) {
                    sender.sendMessage("§7§l[§8§lST§c§lS§7§l]§c すでに false に設定済みです！");
                    return true;
                }
                config.set("STSOperation",false);
                saveConfig();
                sender.sendMessage("§7§l[§8§lST§c§lS§7§l]§e config設定 STSOperation を false に変更しました！");
                return true;
            }else {
                //"args[0]"が"true"、"on"、"false"、"off"でもない場合"/sts"でヘルプを表示させるように促します！
                sender.sendMessage("§7§l[§8§lST§c§lS§7§l]§4 指定を間違えています！");
                sender.sendMessage("§7§l[§8§lST§c§lS§7§l]§f 詳しくは /sts でヘルプを表示してください！");
                return true;
            }
        }
        return false;
    }
    @Override
    //たぶほじゅー(´･ω･`)
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (!command.getName().equalsIgnoreCase("sts")) return super.onTabComplete(sender, command, alias, args);
        if (args.length == 1) {
            if (args[0].length() == 0) {
                return Arrays.asList("on", "true","off","false");
            } else {
                if ("on".startsWith(args[0])) return Collections.singletonList("on");
                else if ("true".startsWith(args[0])) return Collections.singletonList("true");
                else if ("off".startsWith(args[0])) return Collections.singletonList("off");
                else if ("false".startsWith(args[0])) return Collections.singletonList("false");
            }
        }
        return super.onTabComplete(sender, command, alias, args);
    }
}
