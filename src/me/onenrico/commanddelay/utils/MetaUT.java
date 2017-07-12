package me.onenrico.commanddelay.utils;

import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.entity.LivingEntity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import me.onenrico.commanddelay.main.Core;

public class MetaUT {

	public static final MetadataValue createMetadata(Object value) {
		return new FixedMetadataValue(Core.getThis(), value);
	}

	public static final MetadataValue createMetadata(Integer value) {
		return new FixedMetadataValue(Core.getThis(), value);
	}

	public static final MetadataValue createMetadata(String value) {
		return new FixedMetadataValue(Core.getThis(), value);
	}

	public static final MetadataValue createMetadata(Boolean value) {
		return new FixedMetadataValue(Core.getThis(), value);
	}

	public static final MetadataValue createMetadata(List<String> value) {
		return new FixedMetadataValue(Core.getThis(), value);
	}

	public static final MetadataValue getMetadata(LivingEntity entity, String metadata) {
		return getMetadata(entity, metadata, 0);
	}

	public static final MetadataValue getMetadata(LivingEntity entity, String metadata, int index) {
		return entity.getMetadata(metadata).get(index);
	}

	public static final MetadataValue getMetadata(Block block, String metadata) {
		return getMetadata(block, metadata, 0);
	}

	public static final MetadataValue getMetadata(Block block, String metadata, int index) {
		return block.getMetadata(metadata).get(index);
	}

	public static void removeMeta(LivingEntity entity, String metadata) {
		entity.removeMetadata(metadata, Core.getThis());
	}

	public static void removeMeta(Block block, String metadata) {
		block.removeMetadata(metadata, Core.getThis());
	}

	public static final boolean isExpired(LivingEntity entity, String metadata) {
		return isExpired(entity, metadata, true);
	}

	public static final boolean isExpired(LivingEntity entity, String metadata, Boolean remove) {
		if (!entity.hasMetadata(metadata)) {
			return true;
		} else {
			final long expired = getMetadata(entity, metadata).asLong();
			final long time = System.currentTimeMillis();
			if (time < expired) {
				return false;
			} else {
				if (remove) {
					removeMeta(entity, metadata);
				}
				return true;
			}
		}
	}

	public static final boolean isThere(LivingEntity entity, String metadata) {
		return isThere(entity, metadata, null);
	}

	public static final boolean isThere(LivingEntity entity, String metadata, Object args) {
		if (!entity.hasMetadata(metadata)) {
			return false;
		} else {
			if (args != null) {
				if (entity.getMetadata(metadata).equals(createMetadata(args))) {
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		}
	}

	public static final boolean isThere(Block block, String metadata) {
		return isThere(block, metadata, null);
	}

	public static final boolean isThere(Block block, String metadata, Object args) {
		if (!block.hasMetadata(metadata)) {
			return false;
		} else {
			if (args != null) {
				if (block.getMetadata(metadata).equals(createMetadata(args))) {
					return true;
				} else {
					return false;
				}
			} else {
				return true;
			}
		}
	}

	public static final void setMetaData(LivingEntity entity, String metadata, Object arg) {
		entity.setMetadata(metadata, createMetadata(arg));
	}

	public static final void setMetaDataTimed(LivingEntity entity, String metadata, long time) {
		final long duration = System.currentTimeMillis() + time;
		entity.setMetadata(metadata, createMetadata(duration));
	}

	public static final void setMetaData(Block block, String metadata, Object arg) {
		block.setMetadata(metadata, createMetadata(arg));
	}

	public static final void setMetaDataTimed(Block block, String metadata, long time) {
		final long duration = System.currentTimeMillis() + time;
		block.setMetadata(metadata, createMetadata(duration));
	}
}
