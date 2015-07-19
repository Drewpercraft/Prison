package me.sirfaizdat.prison.mines;

import me.sirfaizdat.prison.ranks.Ranks;
import me.sirfaizdat.prison.ranks.UserInfo;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import com.google.common.primitives.Booleans;

public class MinesBlockListener implements Listener {
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if (e.getBlock().getLocation().getWorld().getName().equalsIgnoreCase("prison")) {
			if (e.getPlayer().hasPermission("prison.mines.bypassprotection")) {
				return;
			}
			boolean nonMineBlock = true;
			for (Mine m : Mines.i.mm.getMines().values()) {
				if (m.withinMine(e.getBlock().getLocation())) {
					nonMineBlock = false;
					if (m.ranks == null) break;
					if (m.ranks.size() < 1) break;
					UserInfo info = Ranks.i.getUserInfo(e.getPlayer().getName());
					if (info == null) break;
					boolean[] conditions = new boolean[m.ranks.size()];
					for (int i = 0; i < m.ranks.size(); i++) {
						conditions[i] = info.getCurrentRank().getName().equals(m.ranks.get(i));
					}
					if (!Booleans.contains(conditions, true)) {
						e.setCancelled(true);
					}
				}
			}
			if (nonMineBlock && !e.getPlayer().hasPermission("prison.mines.bypassgrief")) {
				e.setCancelled(true);			
			}
		}
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e) {
		if (e.getBlock().getLocation().getWorld().getName().equalsIgnoreCase("prison")) {
			if (e.getPlayer().hasPermission("prison.mines.bypassprotection")) {
				return;
			}			
			boolean nonMineBlock = true;
			Material placedBlock = e.getBlock().getType();
			if (placedBlock != Material.LAVA && placedBlock != Material.WATER) {
				for (Mine m : Mines.i.mm.getMines().values()) {
					if (m.withinMine(e.getBlock().getLocation())) {
						nonMineBlock = false;
						if (m.ranks == null) break;
						if (m.ranks.size() < 1) break;
						UserInfo info = Ranks.i.getUserInfo(e.getPlayer().getName());
						if (info == null) break;
						boolean[] conditions = new boolean[m.ranks.size()];
						for (int i = 0; i < m.ranks.size(); i++) {
							conditions[i] = info.getCurrentRank().getName().equals(m.ranks.get(i));
						}
						if (!Booleans.contains(conditions, true)) {
							e.setCancelled(true);
						}
					}
				}
			}
			if (nonMineBlock && !e.getPlayer().hasPermission("prison.mines.bypassgrief")) {
				Bukkit.getLogger().warning("Prevented " + placedBlock + " placement by " + e.getPlayer().getDisplayName());
				e.setCancelled(true);			
			}
		}
	}

	@EventHandler
	public void onBucketEmpty(PlayerBucketEmptyEvent e) {
		if (e.getBlockClicked().getWorld().getName().equalsIgnoreCase("prison")) {
			if (e.getPlayer().hasPermission("prison.mines.bypassprotection")) {
				return;
			}
			Bukkit.getLogger().warning("Prevented " + e.getBucket() + " placement by " + e.getPlayer().getDisplayName());
			e.setCancelled(true);
		}
	}
}
