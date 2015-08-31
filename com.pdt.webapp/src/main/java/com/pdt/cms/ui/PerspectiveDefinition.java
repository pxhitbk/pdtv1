package com.pdt.cms.ui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.pdt.cms.ui.model.GroupModel;
import com.pdt.cms.ui.perspective.Perspective;
import com.pdt.core.util.CommonValidation;

/**
 * Singleton object that define and initialize all perspectives by spring bean in perspective.xml.
 *
 * @author hungpx
 *
 */
public class PerspectiveDefinition {

	// private static final Logger LOGGER = LoggerFactory.getLogger(PerspectiveDefinition.class);

	/** Hierarchy map: Map <group name, <Map <path_name, perspective>> */
	static final Map<String, Map<String, Perspective>> PERSPECTIVE_MAP = new HashMap<>();
	static final Map<String, GroupModel> DECLARED_GROUPS = new HashMap<>();
	static final Map<String, Perspective> DECLARED_PERSPECTIVES = new HashMap<>();
	private static PerspectiveDefinition instance;

	private PerspectiveDefinition() {
	}

	public static PerspectiveDefinition createPerspective(List<Perspective> perspectives) {
		CommonValidation.forceNotNullOrEmpty("Perspective list cannot be null or empty", perspectives);
		if (instance == null) {
			instance = new PerspectiveDefinition();
		}

		Set<String> tempGroups = new HashSet<>();
		for (Perspective perspective : perspectives) {
			CommonValidation.forceNotNullOrEmpty("Group id cannot be null", perspective.getGroup().getId());
			boolean added = tempGroups.add(perspective.getGroup().getId());
			if (added) {
				DECLARED_GROUPS.put(perspective.getGroup().getId(), perspective.getGroup());
			}
		}

		// TODO: sort DECLARED_GROUPS

		Map<String, Perspective> perspectivePathMap = null;
		;
		for (final String gid : DECLARED_GROUPS.keySet()) {
			List<Perspective> filteredPerspectiveByGroup = FluentIterable.from(perspectives)
					.filter(new Predicate<Perspective>() {

						@Override
						public boolean apply(Perspective input) {
							return input.getGroup().getId().equals(gid);
						}
					}).toList();

			perspectivePathMap = new HashMap<>();

			for (Perspective perspective : filteredPerspectiveByGroup) {
				perspectivePathMap.put(perspective.getModel().getPath(), perspective);
				DECLARED_PERSPECTIVES.put(perspective.getModel().getPath(), perspective);
			}

			if (perspectivePathMap.size() > 0)
				PERSPECTIVE_MAP.put(gid, perspectivePathMap);
		}

		// TODO: sort PERSPECTIVE_MAP

		return instance;
	}

	/**
	 * Get singleton perspective definition
	 *
	 * @return
	 */
	public static PerspectiveDefinition getInstance() {
		return instance;
	}

	/**
	 * Get the main container that hold all the content and side bar of perspective.
	 *
	 * @param path
	 * @return
	 */
	public static Perspective getPerspectiveContainer(String path) {
		CommonValidation.forceNotNull("Cannot pass a null path", path);

		return DECLARED_PERSPECTIVES.get(path);
	}

	/**
	 *
	 * @param groupId
	 * @return map contains path as key and perspective as value
	 */
	public static Map<String, Perspective> getPerspectivesByGroup(String groupId) {
		return PERSPECTIVE_MAP.get(groupId);
	}

	public static GroupModel getGroupModelById(String gid) {
		return DECLARED_GROUPS.get(gid);
	}

}
