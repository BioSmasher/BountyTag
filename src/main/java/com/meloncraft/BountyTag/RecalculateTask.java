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

import org.bukkit.scheduler.BukkitRunnable;

/**
 *
 * @author Gary
 */
public class RecalculateTask extends BukkitRunnable{
    BountyTag plugin;
    
    public RecalculateTask(BountyTag plug) {
        plugin = plug;
    }
    
    public void run() {
        
        //plugin.getLogger().info("Recalculating Bounties");
        plugin.recalculate();
    }
}