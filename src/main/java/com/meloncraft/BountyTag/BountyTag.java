/**
 The MIT License (MIT)

Copyright (c) 2014 Gary Qian 
 
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package com.meloncraft.BountyTag;

import java.text.DecimalFormat;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.kitteh.tag.TagAPI;

public final class BountyTag extends JavaPlugin {
    public ArrayList<PlayerValue> players;
    DecimalFormat format;
    @Override
    public void onEnable() {
        players = new ArrayList<PlayerValue>();
        this.saveDefaultConfig();
        this.getConfig();
	new Listeners(this);
        for (Player p : this.getServer().getOnlinePlayers()) {
            players.add(new PlayerValue(p, 0));
        }
        format = new DecimalFormat("0");
        BukkitTask recalculate1 = new RecalculateTask(this, 0).runTaskTimer(this, 60, this.getConfig().getInt("delay"));
        BukkitTask recalculate2 = new RecalculateTask(this, 1).runTaskTimer(this, 60 + (this.getConfig().getInt("delay") / 2), this.getConfig().getInt("delay"));
    }
    
    @Override
    public void onDisable() {
        
    }
    
    public void recalculate(int half) {
        int index = 0;
        for (PlayerValue p : players) {
            
            if ((half == 0 && index <= this.getServer().getOnlinePlayers().size() / 2) || (half != 0 && index > this.getServer().getOnlinePlayers().size() / 2)) {
                PlayerInventory inv = p.player.getInventory();
                double value = 0;
                for (int i = 0; i < 36; i++) {
                    ItemStack stack = inv.getItem(i);
                    if (stack != null) {
                        try {
                            value += stack.getAmount() * this.getConfig().getDouble("" + stack.getTypeId());
                        }
                        catch (Exception e) {
                            
                        }
                    }
                }
                //this.getLogger().info(p.player.getName() + " " + value);
                p.setValue(value);
                TagAPI.refreshPlayer(p.player);
                
                if (this.getServer().getWorld("world").getBlockAt(p.player.getLocation()).getType() == Material.STONE
                        || this.getServer().getWorld("world").getBlockAt(p.player.getLocation()).getType() == Material.COAL_ORE
                        || this.getServer().getWorld("world").getBlockAt(p.player.getLocation()).getType() == Material.IRON_ORE
                        || this.getServer().getWorld("world").getBlockAt(p.player.getLocation()).getType() == Material.DIRT
                        || this.getServer().getWorld("world").getBlockAt(p.player.getLocation()).getType() == Material.GOLD_ORE
                        || this.getServer().getWorld("world").getBlockAt(p.player.getLocation()).getType() == Material.DIAMOND_ORE
                        || this.getServer().getWorld("world").getBlockAt(p.player.getLocation()).getType() == Material.EMERALD_ORE
                        || this.getServer().getWorld("world").getBlockAt(p.player.getLocation()).getType() == Material.OBSIDIAN) {
                    p.player.teleport(new Location(this.getServer().getWorld("world"), -54.5, 55, 28.5));
                    this.getLogger().info("Teleported " + p.player.getName() + " to Spawn. Unstucked! " + this.getServer().getWorld("world").getBlockAt(p.player.getLocation()));
                }
            }
            index++;
        }
    }
    
    
    public void check() {
        if (players.size() != this.getServer().getOnlinePlayers().size()){
            this.getLogger().severe("LIST SIZE INFORRECT. " + players.size() + "in list and " + this.getServer().getOnlinePlayers().size() + " players online.");
        }
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("retag")) {
            if (!(sender instanceof Player)) {
                for (PlayerValue p : players) {
                    PlayerInventory inv = p.player.getInventory();
                    double value = 0;
                    for (int i = 0; i < 36; i++) {
                        ItemStack stack = inv.getItem(i);
                        if (stack != null) {
                            try {
                                value += stack.getAmount() * this.getConfig().getDouble("" + stack.getTypeId());
                            }
                            catch (Exception e) {
                                
                            }
                        }
                    }
                    p.setValue(value);
                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("bounty")) {
            if (sender instanceof Player) {
                for (PlayerValue p : players) {
                    if (p.equals(sender.getName())) {
                        p.player.sendMessage(ChatColor.DARK_RED + "Your Inventory Value is " + ChatColor.GOLD + "$" + format.format(p.value));
                        break;
                    }
                }
            }

        }
        return false;
    }
}
