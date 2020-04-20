package services;

import org.keycloak.admin.client.resource.AuthorizationResource;
import org.keycloak.admin.client.resource.PoliciesResource;
import org.keycloak.admin.client.resource.UserPolicyResource;
import org.keycloak.representations.idm.authorization.DecisionStrategy;
import org.keycloak.representations.idm.authorization.Logic;
import org.keycloak.representations.idm.authorization.PolicyRepresentation;
import org.keycloak.representations.idm.authorization.UserPolicyRepresentation;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PermissionService {
    private final AuthorizationResource authorizationResource;

    PermissionService(String accessToken, String clientAppName) {
        this.authorizationResource = AuthService.getAuthorization(accessToken, clientAppName);
    }

    private boolean createOrUpdatePolicy(String userId, Set<String> policies) {
        PoliciesResource policiesResource = authorizationResource.policies();
        for (String policy : policies) {
            PolicyRepresentation policyRepresentation = policiesResource.findByName(policy);
            if (policyRepresentation == null) {
                UserPolicyRepresentation representation = new UserPolicyRepresentation();
                representation.setName(policy);
                representation.addUser(userId);
                Response response = policiesResource.user().create(representation);
                if (response.getStatus() != 201) {
                    return false;
                }
                response.close();
            } else {
                UserPolicyResource userPolicyResource = policiesResource.user().findById(policyRepresentation.getId());
                UserPolicyRepresentation representation = userPolicyResource.toRepresentation();
                representation.setName(policy);
                representation.addUser(userId);
                userPolicyResource.update(representation);
            }
        }
        return true;
    }

    private Set<String> getPoliciesByPermissionId(String id) {
        List<PolicyRepresentation> policies = authorizationResource.policies().policy(id).associatedPolicies();
        Set<String> policiesSet = new HashSet<>();
        for (PolicyRepresentation policy : policies)
            policiesSet.add(policy.getName());
        return policiesSet;
    }

    public PolicyRepresentation createClientPermission(
            String userId, String name, String description, Set<String> policies
    ) {
        boolean status = createOrUpdatePolicy(userId, policies);
        if (!status)
            return null;
        PolicyRepresentation permission = new PolicyRepresentation();
        permission.setName(name);
        permission.setType("resource");
        permission.setDescription(description);
        permission.setDecisionStrategy(DecisionStrategy.UNANIMOUS);
        permission.setLogic(Logic.POSITIVE);
        permission.setPolicies(policies);
        Response response = authorizationResource.policies().create(permission);
        permission = response.readEntity(PolicyRepresentation.class);
        response.close();
        return permission;
    }

    public List<PolicyRepresentation> getPermissions() {
        List<PolicyRepresentation> permissionsWithPolicies = new ArrayList<>();
        for (PolicyRepresentation permission : authorizationResource.policies().policies()) {
            permission.setPolicies(getPoliciesByPermissionId(permission.getId()));
            permissionsWithPolicies.add(permission);
        }
        return permissionsWithPolicies;
    }

    public PolicyRepresentation getPermissionByName(String name) {
        try {
            PolicyRepresentation permission = authorizationResource.policies().findByName(name);
            permission.setPolicies(getPoliciesByPermissionId(permission.getId()));
            return permission;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deletePermission(String permissionName) {
        try {
            PoliciesResource policiesResource = authorizationResource.policies();
            PolicyRepresentation policy = policiesResource.findByName(permissionName);
            if (policy != null) {
                policiesResource.policy(policy.getId()).remove();
                if (policiesResource.findByName(permissionName) == null)
                    return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
