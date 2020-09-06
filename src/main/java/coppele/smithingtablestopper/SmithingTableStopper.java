package coppele.smithingtablestopper;


import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.plugin.java.JavaPlugin;

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
        //クリックしたのが"Upgrade Gear"の場合以下のプログラムを実行します！(∩´∀｀)∩
        if (e.getView().getType().getDefaultTitle().equals("Upgrade Gear")) {
            config = getConfig();
            if (e.getPlayer().hasPermission("SmithingTableStopper.sts.op")) return;
            if (!config.contains("STSOperation")){
                getLogger().warning("config.ymlが存在しません！");
            } else if (config.getString("STSOperation") == "true") {
                e.setCancelled(true);
                e.getPlayer().sendMessage("§7§l[§8§lST§c§lS§7§l]§4 申し訳ありませんが鍛冶台は使用できません…。");
            } else if (!(config.getString("STSOperation") == "true" || config.getString("STSOperation") == "false")) {
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
                sender.sendMessage("§7§l[§8§lST§c§lS§7§l]§8§l S§7mithing§8§lT§7able§c§lS§ctopper Help");
                sender.sendMessage("§7§l[§8§lST§c§lS§7§l]§f /sts <true|false> -起動/停止します");
                sender.sendMessage("§7§l[§8§lST§c§lS§7§l]§f /sts <on|off> -起動/停止します");
                return true;
                //"args[0]"が"true"または"on"の場合"STSOperation"を"true"にします！
            }else if (args[0].equals("true")||args[0].equals("on")) {
                config.set("STSOperation","true");
                saveConfig();
                sender.sendMessage("§7§l[§8§lST§c§lS§7§l]§e config設定 STSOperation を true に変更しました！");
                return true;
                //"args[0]"が"false"または"off"の場合"STSOperation"を"false"にします！
            }else if (args[0].equals("false")||args[0].equals("off")) {
                config.set("STSOperation","false");
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
}
