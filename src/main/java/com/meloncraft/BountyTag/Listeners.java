/**
 The MIT License (MIT)

Copyright (c) 2014 Gary Qian 
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/

package com.meloncraft.BountyTag;

import com.meloncraft.levelsystem.API;
import java.text.DecimalFormat;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;
import org.kitteh.tag.TagAPI;

public class Listeners implements Listener{
    BountyTag plugin;
    DecimalFormat format;
    public Listeners(BountyTag plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
        format = new DecimalFormat(plugin.getConfig().getString("format"));
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        plugin.players.add(new PlayerValue(event.getPlayer(), 0));
        
    }
    
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        for (PlayerValue p : plugin.players) {
            if (p.equals(event.getPlayer())) {
                plugin.players.remove(p);
                break;
            }
        }
    }
    
    @EventHandler
    public void onNameTag(AsyncPlayerReceiveNameTagEvent event) {
        PlayerValue playerValue = null;
        for (PlayerValue p : plugin.players) {
            if (p.equals(event.getNamedPlayer())) {
                playerValue = p;
                break;
            }
        }
        if (playerValue != null) {
            //if (playerValue.player.getName().length() <= 10) {
            if (playerValue.value < 500) event.setTag("" + /* + ChatColor.GREEN + API.getLevel(playerValue.player.getName()) + */ChatColor.GOLD + "1k " + ChatColor.WHITE + playerValue.player.getName() );
            else event.setTag("" + /* + ChatColor.GREEN + API.getLevel(playerValue.player.getName()) + */ChatColor.GOLD + "" + format.format(playerValue.getValue() / 1000.0) + "k " + ChatColor.WHITE + playerValue.player.getName() );
            //}
            //else {
            //event.setTag("" + ChatColor.GREEN + API.getLevel(playerValue.player.getName()) + " " + ChatColor.WHITE + playerValue.player.getName().substring(0, 10) + ChatColor.GOLD + " $" + playerValue.getValue());
            //}
            
        }
        else {
            event.setTag("" + ChatColor.GREEN + API.getLevel(event.getNamedPlayer().getName()) + " " + ChatColor.WHITE + event.getNamedPlayer().getName() + ChatColor.GOLD + " $0");
        }
    }
    
    @EventHandler
    public void onPickup(PlayerPickupItemEvent event) {
        
        PlayerValue playerValue = null;
        for (PlayerValue p : plugin.players) {
            if (p.equals(event.getPlayer())) {
                playerValue = p;
                break;
            }
        }
        try {
            playerValue.setValue(playerValue.value + event.getItem().getItemStack().getAmount() * plugin.getConfig().getDouble("" + event.getItem().getItemStack().getTypeId()));
        }
        catch (Exception e) {
            
        }
        TagAPI.refreshPlayer(playerValue.player);
    }
    
    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        
        PlayerValue playerValue = null;
        for (PlayerValue p : plugin.players) {
            if (p.equals(event.getPlayer())) {
                playerValue = p;
                break;
            }
        }
        try {
            playerValue.setValue(playerValue.value - event.getItemDrop().getItemStack().getAmount() * plugin.getConfig().getDouble("" + event.getItemDrop().getItemStack().getTypeId()));
        }
        catch (Exception e) {
            
        }
        TagAPI.refreshPlayer(playerValue.player);
    }
    
   /* @EventHandler
    public void onSuffocate(P)*/
}
