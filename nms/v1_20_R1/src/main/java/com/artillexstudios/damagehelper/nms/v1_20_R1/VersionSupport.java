package com.artillexstudios.damagehelper.nms.v1_20_R1;

import net.minecraft.util.MathHelper;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.EnumMonsterType;
import net.minecraft.world.entity.ai.attributes.GenericAttributes;
import net.minecraft.world.item.ItemSword;
import net.minecraft.world.item.enchantment.EnchantmentManager;
import org.bukkit.craftbukkit.v1_20_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_20_R1.inventory.CraftItemStack;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;
import java.util.List;

public class VersionSupport {

    public void attack(Entity source, Entity entity, ItemStack item) {
        net.minecraft.world.entity.EntityLiving src = (EntityLiving) ((CraftEntity) source).getHandle();
        net.minecraft.world.entity.Entity victim = ((CraftEntity) entity).getHandle();
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);

        float f = (float) src.b(GenericAttributes.f) == 0.0f ? 1.0f : (float) src.b(GenericAttributes.f);
        float f1;

        if (victim instanceof EntityLiving) {
            f1 = EnchantmentManager.a(nmsItem, ((EntityLiving) victim).eN());
        } else {
            f1 = EnchantmentManager.a(nmsItem, EnumMonsterType.a);
        }

        float f2 = 1;

        f *= 0.2F + f2 * f2 * 0.8F;
        f1 *= f2;

        if (f > 0.0F || f1 > 0.0F) {
            byte b0 = 0;
            int i = b0 + EnchantmentManager.c(src);
            f += f1;
            boolean flag3 = nmsItem.d() instanceof ItemSword;

            int j = EnchantmentManager.d(src);

            if (victim instanceof EntityLiving) {
                if (j > 0 && !victim.bL()) {
                    victim.setSecondsOnFire(1, false);
                }
            }

            boolean flag5 = victim.a(src.dJ().b(src), f);

            if (flag5) {
                if (i > 0) {
                    if (victim instanceof EntityLiving) {
                        ((EntityLiving) victim).q((float) i * 0.5F, MathHelper.a(src.dy() * 0.017453292F), -MathHelper.b(src.dy() * 0.017453292F));
                    } else {
                        victim.j(-MathHelper.a(src.dy() * 0.017453292F) * (float) i * 0.5F, 0.1D, MathHelper.b(src.dy() * 0.017453292F) * (float) i * 0.5F);
                    }

                }
            }

            if (flag3) {
                float f4 = 1.0F + EnchantmentManager.a(src) * f;
                List<EntityLiving> list = src.dI().a(EntityLiving.class, victim.cE().c(1.0D, 0.25D, 1.0D));
                Iterator iterator = list.iterator();

                while (iterator.hasNext()) {
                    EntityLiving entityliving = (EntityLiving) iterator.next();

                    if (entityliving != src && entityliving != victim && !src.p(entityliving) && (!(entityliving instanceof ArmorStand) || !((ArmorStand) entityliving).isMarker()) && src.f(entityliving) < 9.0D) {
                        if (entityliving.a(src.dJ().b(src), f4)) {
                            entityliving.q(0.4000000059604645D, MathHelper.a(src.dy() * 0.017453292F), -MathHelper.b(src.dy() * 0.017453292F));
                        }
                    }
                }
            }

            src.x(victim);
            if (victim instanceof EntityLiving) {
                EnchantmentManager.a((EntityLiving) victim, src);
            }

            EnchantmentManager.b(src, victim);

            if (victim instanceof EntityLiving) {
                if (j > 0) {
                    victim.setSecondsOnFire(j * 4, false);
                }
            }
        }
    }
}


