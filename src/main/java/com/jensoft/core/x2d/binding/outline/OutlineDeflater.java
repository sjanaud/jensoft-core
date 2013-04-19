package com.jensoft.core.x2d.binding.outline;

import org.w3c.dom.Element;

import com.jensoft.core.plugin.outline.OutlinePlugin;
import com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater;

public class OutlineDeflater extends AbstractX2DPluginDeflater<OutlinePlugin> {		

	public OutlineDeflater() {
		super(new OutlinePlugin());
		setXSIType("OutlinePlugin");
	}

	public OutlineDeflater(OutlinePlugin plugin) {
		super(plugin);
		setXSIType("OutlinePlugin");
	}
	
//	<plugin xsi:type="OutlinePlugin">
//	<outline-color>
//		<r>0</r>
//		<g>0</g>
//		<b>0</b>
//		<a>255</a>
//	</outline-color>
//	</plugin>

	/* (non-Javadoc)
	 * @see com.jensoft.core.x2d.binding.AbstractX2DPluginDeflater#deflate()
	 */
	@Override
	public Element deflate() {		
		return null;
	}

}
