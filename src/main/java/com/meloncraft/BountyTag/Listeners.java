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

import com.meloncraft.levelsystem.API;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;

public class Listeners implements Listener{
    BountyTag plugin;
    public Listeners(BountyTag plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
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
                event.setTag("" + /* + ChatColor.GREEN + API.getLevel(playerValue.player.getName()) + */ChatColor.GOLD + "$" + playerValue.getValue() + " " + ChatColor.WHITE + playerValue.player.getName() );
            //}
            //else {
                //event.setTag("" + ChatColor.GREEN + API.getLevel(playerValue.player.getName()) + " " + ChatColor.WHITE + playerValue.player.getName().substring(0, 10) + ChatColor.GOLD + " $" + playerValue.getValue());
            //}
            
        }
        else {
            event.setTag("" + ChatColor.GREEN + API.getLevel(event.getNamedPlayer().getName()) + " " + ChatColor.WHITE + event.getNamedPlayer().getName() + ChatColor.GOLD + " $0");
        }
    }
    
}
