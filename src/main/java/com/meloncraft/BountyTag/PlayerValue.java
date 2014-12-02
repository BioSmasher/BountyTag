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

import org.bukkit.entity.Player;

/**
 *
 * @author Gary
 */
public class PlayerValue {
    Player player;
    double value;
    
    public PlayerValue(Player player, double value) {
        this.player = player;
        this.value = value;
    }
    
    public void setValue(double value) {
        this.value = value;
    }
    
    public int getValue() {
        return (int) value;
    }
    
    public Player getPlayer() {
        return player;
    }
    
    public boolean equals(Player player) {
        if (this.player.getName().equals(player.getName())) return true;
        return false;
    }
}
