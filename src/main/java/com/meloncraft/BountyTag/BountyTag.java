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

import java.util.ArrayList;
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
     
    @Override
    public void onEnable() {
        players = new ArrayList<PlayerValue>();
        this.saveDefaultConfig();
        this.getConfig();
	new Listeners(this);
        for (Player p : this.getServer().getOnlinePlayers()) {
            players.add(new PlayerValue(p, 0));
        }
        BukkitTask recalculate = new RecalculateTask(this).runTaskTimer(this, 40, this.getConfig().getInt("delay"));
    }
    
    @Override
    public void onDisable() {
        
    }
    
    public void recalculate() {
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
            //this.getLogger().info(p.player.getName() + " " + value);
            p.setValue(value);
            TagAPI.refreshPlayer(p.player);
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
        return false;
    }
}
