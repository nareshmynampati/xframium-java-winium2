package org.xframium.driver.container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.xframium.artifact.ArtifactType;

public class DriverContainer
{
    private List<String> perfectoPersonas = new ArrayList<String>( 20 );
    private boolean perfectoWindTunnel = false;
    private boolean displayReport = false;
    private boolean smartCaching = false;
    private boolean dryRun = false;
    private boolean embeddedServer = false;
    private String stepTags = "";
    private String testTags = "";
    private String deviceInterrupts = "";
    private Map<String,String> propertyMap = new HashMap<String,String>( 20 );
    private String reportFolder;
    private List<ArtifactType> artifactList = new ArrayList<ArtifactType>( 20 );
    private boolean secureCloud = false;
    
    public boolean isSecureCloud()
    {
        return secureCloud;
    }

    public void setSecureCloud( boolean secureCloud )
    {
        this.secureCloud = secureCloud;
    }

    private List<String> testNames = new ArrayList<String>( 20 );

    public DriverContainer()
    {
        
    }

    public String getReportFolder()
    {
        return reportFolder;
    }

    public void setReportFolder( String reportFolder )
    {
        this.reportFolder = reportFolder;
    }

    public List<ArtifactType> getArtifactList()
    {
        return artifactList;
    }

    public boolean isArtifactEnabled( ArtifactType aType )
    {
        for ( ArtifactType a : artifactList )
        {
            if ( a.equals( aType ) )
                return true;
        }
        
        return false;
    }
    
    public void setArtifactList( String artifactList )
    {
        if ( artifactList != null && !artifactList.isEmpty() )
        {
            String[] artifacts = artifactList.split( "," );
            
            for ( String artifact : artifacts )
            {
                try
                {
                    this.artifactList.add( ArtifactType.valueOf( artifact.toUpperCase() ) );
                }
                catch( Exception e )
                {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void addArtifact( ArtifactType artifactType )
    {
        artifactList.add( artifactType );
    }
    
    public void setArtifactList( List<ArtifactType> artifactList )
    {
        this.artifactList = artifactList;
    }

    public String getDeviceInterrupts()
    {
        return deviceInterrupts;
    }

    public void setDeviceInterrupts( String deviceInterrupts )
    {
        this.deviceInterrupts = deviceInterrupts;
    }

    public List<String> getPerfectoPersonas()
    {
        return perfectoPersonas;
    }

    public void setPerfectoPersonas( String perfectoPersonas )
    {
        if ( perfectoPersonas != null )
            this.perfectoPersonas.addAll( Arrays.asList( perfectoPersonas.split( "," ) ) );
        
        perfectoWindTunnel = this.perfectoPersonas.size() > 0;
        
    }
    
    public void setPerfectoPersonas( List<String> perfectoPersonas )
    {
        this.perfectoPersonas = perfectoPersonas;
        perfectoWindTunnel = this.perfectoPersonas.size() > 0;
    }

    public boolean isPerfectoWindTunnel()
    {
        return perfectoWindTunnel;
    }

    public void setPerfectoWindTunnel( boolean perfectoWindTunnel )
    {
        this.perfectoWindTunnel = perfectoWindTunnel;
    }

    public boolean isDisplayReport()
    {
        return displayReport;
    }

    public void setDisplayReport( boolean displayReport )
    {
        this.displayReport = displayReport;
    }

    public boolean isSmartCaching()
    {
        return smartCaching;
    }

    public void setSmartCaching( boolean smartCaching )
    {
        this.smartCaching = smartCaching;
    }

    public boolean isDryRun()
    {
        return dryRun;
    }

    public void setDryRun( boolean dryRun )
    {
        this.dryRun = dryRun;
    }

    public boolean isEmbeddedServer()
    {
        return embeddedServer;
    }

    public void setEmbeddedServer( boolean embeddedServer )
    {
        this.embeddedServer = embeddedServer;
    }

    public String getStepTags()
    {
        return stepTags;
    }

    public void setStepTags( String stepTags )
    {
        this.stepTags = stepTags;
    }

    public String getTestTags()
    {
        return testTags;
    }

    public void setTestTags( String testTags )
    {
        this.testTags = testTags;
    }

    public Map<String, String> getPropertyMap()
    {
        return propertyMap;
    }

    public void setPropertyMap( Map<String, String> propertyMap )
    {
        this.propertyMap = propertyMap;
    }

    public List<String> getTestNames()
    {
        return testNames;
    }

    public void setTestNames( String testNames )
    {
        if ( testNames != null )
            this.testNames.addAll( Arrays.asList( testNames.split( "," ) ) );
    }
    
    public void setTestNames( List<String> testNames )
    {
        this.testNames = testNames;
    }
    
    
    
}
