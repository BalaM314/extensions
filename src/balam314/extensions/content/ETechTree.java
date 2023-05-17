package balam314.extensions.content;

import arc.func.*;
import arc.struct.*;
import mindustry.content.*;
import mindustry.content.TechTree.*;
import mindustry.ctype.*;
import mindustry.game.Objectives.*;
import mindustry.type.*;

import static mindustry.content.Items.*;
import static mindustry.content.Blocks.*;
import static mindustry.content.Liquids.*;
import static balam314.extensions.content.EItems.*;
import static balam314.extensions.content.EBlocks.*;
import static balam314.extensions.content.ELiquids.*;

public class ETechTree {
	private static TechNode context = null;

	public static TechNode node(UnlockableContent content, ItemStack[] requirements, Seq<Objective> objectives, Runnable children){
		TechNode node = new TechNode(context, content, requirements);
		if(objectives != null) node.objectives.addAll(objectives);

		TechNode prev = context;
		context = node;
		children.run();
		context = prev;

		return node;
	}
	public static TechNode node(UnlockableContent content, Runnable children){
		return node(content, content.researchRequirements(), Seq.with(), children);
	}

	public static TechNode nodeProd(UnlockableContent content){
		return nodeProd(content, Seq.with(), () -> {});
	}

	public static TechNode nodeProd(UnlockableContent content, Runnable children){
		return nodeProd(content, Seq.with(), children);
	}

	public static TechNode nodeProd(UnlockableContent content, Seq<Objective> objectives){
		return nodeProd(content, objectives, () -> {});
	}

	public static TechNode nodeProd(UnlockableContent content, Seq<Objective> objectives, Runnable children){
		return node(content, content.researchRequirements(), objectives.add(new Produce(content)), children);
	}

	public static void vanillaNode(UnlockableContent content, Runnable children){
		TechNode prev = context;
		context = getNode(content);
		children.run();
		context = prev;
	}

	public static TechNode getNode(UnlockableContent content){
		return getNode(content, Planets.serpulo);
	}
	public static TechNode getNode(UnlockableContent content, Planet planet){
		return getNode(planet.techTree, n -> n.content == content);
	}
	public static TechNode getNode(TechNode tree, Boolf<TechNode> cons){
		//recursion ftw
		if(cons.get(tree)) return tree;
		for(TechNode node : tree.children){
			TechNode out = getNode(node, cons);
			if(out != null) return out;
		}
		return null;
	}


	public static void load(){
		//Items/liquids
		vanillaNode(thorium, () -> {
			nodeProd(iridium, Seq.with(/*new OnSector(ESectorPresets.meteor)*/), () -> {
				nodeProd(advancedCoolant, Seq.with(new Produce(Liquids.cryofluid)));
				nodeProd(protactinium, Seq.with(new Produce(phaseFabric)), () -> {
					nodeProd(protactiniumPlasma);
					nodeProd(radiantAlloy);
				});
			});
		});
		//
	}
}